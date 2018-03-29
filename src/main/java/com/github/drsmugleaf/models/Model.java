package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 16/03/2018.
 */
public abstract class Model<T extends Model<T>> {

    @Nonnull
    public List<T> get(@Nonnull T model) throws SQLException {
        List<T> models = new ArrayList<>();

        PreparedStatement statement;
        StringBuilder query = new StringBuilder();
        query
                .append("SELECT * FROM ")
                .append(escape(getTableName(model)));

        Set<Map.Entry<Field, Object>> columns = getColumns(model);
        if (!columns.isEmpty()) {
            query.append(" WHERE ");
        }

        Iterator<Map.Entry<Field, Object>> iterator = columns.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Field, Object> entry = iterator.next();
            String columnName = getColumnName(entry.getKey());

            query
                    .append(columnName)
                    .append(" = ?");

            if (iterator.hasNext()) {
                query.append(" AND ");
            }
        }

        try {
            statement = Database.conn.prepareStatement(query.toString());

            int i = 1;
            for (Map.Entry<Field, Object> column : columns) {
                statement.setObject(i, column.getValue());
                i++;
            }

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                T row = newInstance(model);
                for (Map.Entry<Field, Object> entry : getFields(model).entrySet()) {
                    Field field = entry.getKey();
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    field.set(row, result.getObject(columnAnnotation.name()));
                }
                models.add(row);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            BanterBot4J.LOGGER.error("Error retrieving " + model.getClass().getName(), e);
        }

        return models;
    }

    public void save(@Nonnull T model) throws SQLException {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();
        StringBuilder querySet = new StringBuilder();

        queryInsert
                .append("INSERT INTO ")
                .append(escape(getTableName(model)))
                .append(" (");
        queryValues.append("VALUES(");
        queryConflict.append("ON CONFLICT (");
        querySet.append("DO UPDATE SET ");

        Set<Map.Entry<Field, Object>> columns = getColumns(model);
        Iterator<Map.Entry<Field, Object>> iterator = columns.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Field, Object> entry = iterator.next();
            Field field = entry.getKey();
            String columnName = getColumnName(field);

            queryInsert.append(columnName);
            queryValues.append("?");
            if (isID(field)) {
                queryConflict.append(columnName);
            }
            querySet
                    .append(columnName)
                    .append(" = ?");

            if (iterator.hasNext()) {
                queryInsert.append(", ");
                queryValues.append(", ");
                if (isID(field)) {
                    queryConflict.append(", ");
                }
                querySet.append(", ");
            } else {
                queryInsert.append(") ");
                queryValues.append(") ");
                queryConflict.append(") ");
                querySet.append(" ");
            }
        }

        query
                .append(queryInsert)
                .append(queryValues)
                .append(queryConflict)
                .append(querySet);

        PreparedStatement statement = Database.conn.prepareStatement(query.toString());
        iterator = columns.iterator();
        int i = 1;
        int size = columns.size();
        while (iterator.hasNext()) {
            Map.Entry<Field, Object> entry = iterator.next();
            Object value = entry.getValue();
            statement.setObject(i, value);
            statement.setObject(i + size, value);
            i++;
        }

        System.out.println(statement);

        statement.executeUpdate();
    }

    @Nonnull
    private Set<Map.Entry<Field, Object>> getColumns(@Nonnull T model) {
        Set<Map.Entry<Field, Object>> fields = getFields(model).entrySet();
        fields.removeIf(entry -> entry.getValue() == null);
        return fields;
    }

    @Nonnull
    private String getColumnName(@Nonnull Field field) {
        String columnName;
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        Class<?> fieldClass = field.getClass();
        Table tableAnnotation = fieldClass.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null) {
            columnName = columnAnnotation.name();
        } else {
            columnName = tableAnnotation.name() + "." + columnAnnotation.name();
        }

        return columnName;
    }

    @Nonnull
    private Map<Field, Object> getFields(@Nonnull T model) {
        Map<Field, Object> fields = new HashMap<>();

        for (Field field : model.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            field.setAccessible(true);

            Object value;
            try {
                value = field.get(model);
            } catch (IllegalAccessException e) {
                BanterBot4J.LOGGER.error("Error getting value from field", e);
                continue;
            }
            fields.put(field, value);
        }

        return fields;
    }

    @Nonnull
    private String getTableName(@Nonnull T model) {
        return model.getClass().getAnnotation(Table.class).name();
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private T newInstance(@Nonnull T model) throws IllegalAccessException, InstantiationException {
        return (T) model.getClass().newInstance();
    }

    @Nonnull
    private String escape(@Nonnull String s) throws SQLException {
        PreparedStatement statement = Database.conn.prepareStatement("?");
        statement.setString(1, s);
        return statement.toString().replaceFirst("'(.+)'", "$1");
    }

    private boolean isID(@Nonnull Field field) {
        return field.isAnnotationPresent(Column.Id.class);
    }

}
