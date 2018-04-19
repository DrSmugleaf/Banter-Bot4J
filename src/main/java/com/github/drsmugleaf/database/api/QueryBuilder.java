package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class QueryBuilder<T extends Model> {

    @Nonnull
    private final Class<? extends Model> MODEL;

    @Nonnull
    private final List<TypeResolver> COLUMNS = new ArrayList<>();

    QueryBuilder(@Nonnull Class<T> model) {
        MODEL = model;
        for (Field field : MODEL.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                COLUMNS.add(new TypeResolver(field));
            }
        }
    }

    QueryBuilder(@Nonnull T model) {
        MODEL = model.getClass();
        for (Field field : MODEL.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                COLUMNS.add(new TypeResolver(field));
            }
        }
    }

    @Nonnull
    private String escapedTableName() {
        Table tableAnnotation = MODEL.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();

        try {
            PreparedStatement statement = Database.CONNECTION.prepareStatement("?");
            statement.setString(1, tableName);
            tableName = statement.toString();
            tableName = tableName.replaceFirst("'(.+)'", "$1");
            statement.close();
        } catch (SQLException e) {
            throw new ModelException("Error escaping table name", e);
        }

        return tableName;
    }

    @Nonnull
    private <E extends Model<E>> Map<TypeResolver, Object> getColumns(Model<E> model) {
        Map<TypeResolver, Object> fields = new HashMap<>();

        for (TypeResolver column : COLUMNS) {
            column.FIELD.setAccessible(true);

            Object value;
            try {
                value = column.FIELD.get(model);
            } catch (IllegalAccessException e) {
                Database.LOGGER.error("Error getting value from field", e);
                continue;
            }

            fields.put(column, value);
        }

        return fields;
    }

    @Nonnull
    String createTable() throws InvalidColumnException, SQLException {
        StringBuilder query = new StringBuilder();
        StringBuilder queryReferences = new StringBuilder();
        StringBuilder queryConstraint = new StringBuilder();

        query
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(escapedTableName())
                .append(" (");

        Iterator<TypeResolver> iterator = COLUMNS.iterator();
        while (iterator.hasNext()) {
            TypeResolver column = iterator.next();
            Column columnAnnotation = column.getColumn();
            String name = columnAnnotation.name();
            String type = column.getDataType();

            query
                    .append(name)
                    .append(" ")
                    .append(type);

            if (column.FIELD.isAnnotationPresent(Relation.class)) {
                if (queryReferences.length() != 0) {
                    queryReferences.append(", ");
                }

                TypeResolver relationResolver = column.getRelatedField();
                Relation relationAnnotation = column.getRelation();
                String relatedTableName = relationResolver.getTable().name();
                query
                        .append(" REFERENCES ")
                        .append(relatedTableName)
                        .append(" (")
                        .append(relationAnnotation.columnName())
                        .append(") ON UPDATE CASCADE ON DELETE CASCADE, ");

                queryConstraint
                        .append(relatedTableName)
                        .append("_");

                queryReferences.append(name);
            } else {
                if (column.isID()) {
                    query.append(" PRIMARY KEY ");
                }

                String defaultValue = columnAnnotation.defaultValue();
                if (!defaultValue.isEmpty()) {
                    query
                            .append(" DEFAULT ")
                            .append(defaultValue);
                }

                if (iterator.hasNext() || queryConstraint.length() != 0) {
                    query.append(", ");
                }
            }
        }

        if (queryConstraint.length() != 0) {
            queryConstraint
                    .insert(0, "CONSTRAINT ")
                    .append("pkey PRIMARY KEY ");

            queryReferences
                    .insert(0, "(")
                    .append(")");
        }

        query
                .append(queryConstraint)
                .append(queryReferences)
                .append(")");

        return query.toString();
    }

    @Nonnull
    <E extends Model<E>> String get(Model<E> model) {
        StringBuilder query = new StringBuilder();
        query
                .append(" SELECT * FROM ")
                .append(escapedTableName());

        Set<Map.Entry<TypeResolver, Object>> entries = getColumns(model).entrySet();
        entries.removeIf(entry -> entry.getKey().resolveValue(entry.getValue()) == null);
        if (!entries.isEmpty()) {
            query.append(" WHERE ");

            Iterator<Map.Entry<TypeResolver, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<TypeResolver, Object> entry = iterator.next();
                TypeResolver field = entry.getKey();
                String columnName = field.getColumnName();

                query
                        .append(columnName)
                        .append(" = ? ");

                if (iterator.hasNext()) {
                    query.append(" AND ");
                }
            }
        }

        PreparedStatement statement;
        try {
            statement = Database.CONNECTION.prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ModelException("Error creating SQL query", e);
        }

        int i = 1;
        for (Map.Entry<TypeResolver, Object> entry : entries) {
            TypeResolver field = entry.getKey();
            Object value = entry.getValue();
            try {
                statement.setObject(i, field.resolveValue(value));
            } catch (SQLException e) {
                throw new ModelException("Error setting value in statement", e);
            }
        }

        return statement.toString();
    }

}
