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

    private final Class<T> clazz;

    protected Model(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Nonnull
    public List<T> get(T model) throws SQLException {
        List<T> models = new ArrayList<>();

        PreparedStatement statement;
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ?");

        Set<Map.Entry<Field, Object>> columns = getColumns(model);
        if (!columns.isEmpty()) {
            query.append(" WHERE ");
        }
        Iterator<Map.Entry<Field, Object>> it = columns.iterator();
        while (it.hasNext()) {
            Map.Entry<Field, Object> entry = it.next();
            String columnName = getColumnName(entry.getKey());

            query
                    .append(columnName)
                    .append(" = ?");

            if (it.hasNext()) {
                query.append(" AND ");
            }
        }

        try {
            statement = Database.conn.prepareStatement(query.toString());
            statement.setString(1, getTableName(model));
            String fixedQuery = statement.toString().replaceFirst("'(.+)'", "$1");
            statement = Database.conn.prepareStatement(fixedQuery);

            int i = 1;
            for (Map.Entry<Field, Object> column : columns) {
                statement.setObject(i, column.getValue());
                i++;
            }

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                T row = clazz.newInstance();
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

    @Nonnull
    private Set<Map.Entry<Field, Object>> getColumns(T model) {
        Set<Map.Entry<Field, Object>> fields = getFields(model).entrySet();
        fields.removeIf(entry -> entry.getValue() == null);
        return fields;
    }

    @Nonnull
    private String getColumnName(Field field) {
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
    private Map<Field, Object> getFields(T model) {
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
    private String getTableName(T model) {
        return model.getClass().getAnnotation(Table.class).name();
    }

}
