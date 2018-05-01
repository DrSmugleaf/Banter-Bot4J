package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.Table;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class QueryBuilder<T extends Model<T>> {

    @Nonnull
    private final Class<T> MODEL;

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

    @SuppressWarnings("unchecked")
    QueryBuilder(@Nonnull Model<T> model) {
        MODEL = (Class<T>) model.getClass();
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

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement("?")) {
            statement.setString(1, tableName);
            tableName = statement.toString();
            tableName = tableName.replaceFirst("'(.+)'", "$1");
        } catch (SQLException e) {
            throw new StatementCreationException("Error escaping table name", e);
        }

        return tableName;
    }

    @Nonnull
    String createIfNotExists(@Nonnull Model<T> model) {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();

        queryInsert
                .append("INSERT INTO ")
                .append(escapedTableName())
                .append(" (");
        queryValues.append(" VALUES(");
        queryConflict.append(" ON CONFLICT DO NOTHING ");

        Set<Map.Entry<TypeResolver, Object>> columns = model.getColumns().entrySet();
        Iterator<Map.Entry<TypeResolver, Object>> iterator = columns.iterator();
        while (iterator.hasNext()) {
            Map.Entry<TypeResolver, Object> entry = iterator.next();
            TypeResolver column = entry.getKey();
            String columnName = column.getExternalColumnName();

            queryInsert.append(columnName);
            queryValues.append("?");

            if (iterator.hasNext()) {
                queryInsert.append(", ");
                queryValues.append(", ");
            } else {
                queryInsert.append(") ");
                queryValues.append(") ");
            }
        }

        query
                .append(queryInsert)
                .append(queryValues)
                .append(queryConflict);

        try(PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString())) {
            iterator = columns.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                Map.Entry<TypeResolver, Object> entry = iterator.next();
                TypeResolver field = entry.getKey();
                Object value = entry.getValue();

                try {
                    statement.setObject(i, field.toSQL(value));
                } catch (SQLException e) {
                    throw new StatementValueException(e);
                }

                i++;
            }

            return statement.toString();
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    @Nonnull
    String createTable() {
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

            query.append(column.getColumnDefinition());

            if (column.FIELD.isAnnotationPresent(Relation.class)) {
                if (queryReferences.length() != 0) {
                    queryReferences.append(", ");
                }

                TypeResolver relationResolver = column.getRelatedField();
                String relatedTableName = relationResolver.getTable().name();

                queryConstraint
                        .append(relatedTableName)
                        .append("_");

                queryReferences.append(name);
            }

            if (iterator.hasNext() || queryConstraint.length() != 0) {
                query.append(", ");
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
    String get(@Nonnull Model<T> model) {
        StringBuilder query = new StringBuilder();
        query
                .append(" SELECT * FROM ")
                .append(escapedTableName());

        Set<Map.Entry<TypeResolver, Object>> columns = model.getColumns().entrySet();
        columns.removeIf(entry -> entry.getKey().toSQL(entry.getValue()) == null);
        if (!columns.isEmpty()) {
            query.append(" WHERE ");

            Iterator<Map.Entry<TypeResolver, Object>> iterator = columns.iterator();
            while (iterator.hasNext()) {
                Map.Entry<TypeResolver, Object> entry = iterator.next();
                TypeResolver field = entry.getKey();
                String columnName = field.getExternalColumnName();

                query
                        .append(columnName)
                        .append(" = ? ");

                if (iterator.hasNext()) {
                    query.append(" AND ");
                }
            }
        }

        try(PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString())) {
            int i = 1;
            for (Map.Entry<TypeResolver, Object> entry : columns) {
                TypeResolver field = entry.getKey();
                Object value = entry.getValue();

                try {
                    statement.setObject(i, field.toSQL(value));
                } catch (SQLException e) {
                    throw new StatementValueException(e);
                }

                i++;
            }

            return statement.toString();
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    @Nonnull
    String save(@Nonnull Model<T> model) {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();
        StringBuilder querySet = new StringBuilder();

        queryInsert
                .append(" INSERT INTO ")
                .append(escapedTableName())
                .append(" ( ");
        queryValues.append(" VALUES( ");
        queryConflict.append(" ON CONFLICT ( ");
        querySet.append(" DO UPDATE SET ");

        Set<Map.Entry<TypeResolver, Object>> columns = model.getColumns().entrySet();
        Iterator<Map.Entry<TypeResolver, Object>> iterator = columns.iterator();
        while (iterator.hasNext()) {
            Map.Entry<TypeResolver, Object> entry = iterator.next();
            TypeResolver column = entry.getKey();
            String columnName = column.getExternalColumnName();

            queryInsert.append(columnName);
            queryValues.append("?");

            if (column.isID()) {
                queryConflict.append(columnName);
            }

            querySet
                    .append(columnName)
                    .append(" = ?");

            if (iterator.hasNext()) {
                queryInsert.append(", ");
                queryValues.append(", ");

                if (column.isID()) {
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

        try(PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString())) {
            iterator = columns.iterator();
            int i = 1;
            int size = columns.size();
            while (iterator.hasNext()) {
                Map.Entry<TypeResolver, Object> entry = iterator.next();
                TypeResolver column = entry.getKey();
                Object value = entry.getValue();
                value = column.toSQL(value);

                try {
                    statement.setObject(i, value);
                    statement.setObject(i + size, value);
                } catch (SQLException e) {
                    throw new StatementValueException(e);
                }

                i++;
            }

            return statement.toString();
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    @Nonnull
    String delete(@Nonnull Model<T> model) {
        StringBuilder query = new StringBuilder();

        query
                .append(" DELETE FROM ")
                .append(escapedTableName());

        Set<Map.Entry<TypeResolver, Object>> columns = model.getColumns().entrySet();
        columns.removeIf(entry -> entry.getValue() == null);
        if (!columns.isEmpty()) {
            query.append(" WHERE ");

            Iterator<Map.Entry<TypeResolver, Object>> iterator = columns.iterator();
            while (iterator.hasNext()) {
                Map.Entry<TypeResolver, Object> entry = iterator.next();
                String columnName = entry.getKey().getExternalColumnName();

                query
                        .append(" ( ")
                        .append(columnName)
                        .append(" = ? ")
                        .append(" ) ");

                if (iterator.hasNext()) {
                    query.append(" OR ");
                }
            }
        }

        try(PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString())) {
            int i = 1;
            for (Map.Entry<TypeResolver, Object> entry : columns) {
                TypeResolver column = entry.getKey();
                Object value = entry.getValue();
                value = column.toSQL(value);

                try {
                    statement.setObject(i, value);
                } catch (SQLException e) {
                    throw new StatementValueException(e);
                }

                i++;
            }

            return statement.toString();
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

}
