package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class QueryBuilder<T extends Model<T>> {

    private final Class<T> MODEL;

    private final List<TypeResolver> COLUMNS = new ArrayList<>();

    QueryBuilder(Class<T> model) {
        MODEL = model;
        for (Field field : MODEL.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                COLUMNS.add(new TypeResolver(field));
            }
        }
    }

    @SuppressWarnings("unchecked")
    QueryBuilder(Model<T> model) {
        MODEL = (Class<T>) model.getClass();
        for (Field field : MODEL.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                COLUMNS.add(new TypeResolver(field));
            }
        }
    }

    Query createIfNotExists(Model<T> model) {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();

        Table tableAnnotation = MODEL.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();
        queryInsert
                .append("INSERT INTO ")
                .append(tableName)
                .append(" (");
        queryValues.append(" VALUES(");
        queryConflict.append(" ON CONFLICT DO NOTHING ");

        Set<Map.Entry<TypeResolver, Object>> columns = model.getColumns().entrySet();
        columns.removeIf(entry -> entry.getValue() == null);
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
                .append(queryConflict)
                .append(" RETURNING * ");

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
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

            return new Query(statement);
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    Query createTable() {
        StringBuilder query = new StringBuilder();
        StringBuilder queryConstraint = new StringBuilder();
        StringBuilder queryPrimaryKey = new StringBuilder();

        Table tableAnnotation = MODEL.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();
        query
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(tableName)
                .append(" (");

        Iterator<TypeResolver> iterator = COLUMNS.iterator();
        while (iterator.hasNext()) {
            TypeResolver column = iterator.next();
            Column columnAnnotation = column.getColumnAnnotation();
            String name = columnAnnotation.name();

            query.append(column.getColumnDefinition());

            if (column.isID()) {
                if (queryPrimaryKey.length() > 0) {
                    queryPrimaryKey.append(", ");
                }

                queryConstraint
                        .append(name)
                        .append("_");

                queryPrimaryKey.append(name);
            }


            if (iterator.hasNext() || queryConstraint.length() > 0) {
                query.append(", ");
            }
        }

        if (queryConstraint.length() > 0) {
            queryConstraint
                    .insert(0, "_")
                    .insert(0, tableName)
                    .insert(0, "CONSTRAINT ")
                    .append("pkey ");

            queryPrimaryKey
                    .insert(0, " PRIMARY KEY (")
                    .append(")");
        }

        query
                .append(queryConstraint)
                .append(queryPrimaryKey)
                .append(")");

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());

            return new Query(statement);
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    Query get(Model<T> model) {
        StringBuilder query = new StringBuilder();
        Table tableAnnotation = MODEL.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();
        query
                .append(" SELECT * FROM ")
                .append(tableName);

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

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
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

            return new Query(statement);
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    Query save(Model<T> model) {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();
        StringBuilder querySet = new StringBuilder();

        Table tableAnnotation = MODEL.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();
        queryInsert
                .append(" INSERT INTO ")
                .append(tableName)
                .append(" ( ");
        queryValues.append(" VALUES( ");
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
                if (queryConflict.length() > 0) {
                    queryConflict.append(", ");
                }

                queryConflict.append(columnName);
            }

            querySet
                    .append(columnName)
                    .append(" = ?");

            if (iterator.hasNext()) {
                queryInsert.append(", ");
                queryValues.append(", ");
                querySet.append(", ");
            } else {
                queryInsert.append(") ");
                queryValues.append(") ");
                queryConflict.append(") ");
                querySet.append(" ");
            }
        }

        queryConflict.insert(0, " ON CONFLICT ( ");

        query
                .append(queryInsert)
                .append(queryValues)
                .append(queryConflict)
                .append(querySet)
                .append(" RETURNING * ");

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
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

            return new Query(statement);
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

    Query delete(Model<T> model) {
        StringBuilder query = new StringBuilder();
        Table tableAnnotation = MODEL.getDeclaredAnnotation(Table.class);
        String tableName = tableAnnotation.name();
        query
                .append(" DELETE FROM ")
                .append(tableName);

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

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
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

            return new Query(statement);
        } catch (SQLException e) {
            throw new StatementCreationException(e);
        }
    }

}
