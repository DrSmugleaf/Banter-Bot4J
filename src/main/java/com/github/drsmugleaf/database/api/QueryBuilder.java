package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class QueryBuilder<T extends Model> {

    @Nonnull
    private final Class<T> MODEL;

    @Nonnull
    private final List<Field> COLUMNS = new ArrayList<>();

    QueryBuilder(@Nonnull Class<T> model) {
        MODEL = model;
        for (Field field : MODEL.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                COLUMNS.add(field);
            }
        }
    }

    @Nonnull
    private String escapeTableName() {
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
    String createTable() throws InvalidColumnException, SQLException {
        StringBuilder query = new StringBuilder();
        StringBuilder queryReferences = new StringBuilder();
        StringBuilder queryConstraint = new StringBuilder();

        query
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(escapeTableName())
                .append(" (");

        Iterator<Field> iterator = COLUMNS.iterator();
        while (iterator.hasNext()) {
            Field column = iterator.next();
            TypeResolver columnResolver = new TypeResolver(column);
            Column columnAnnotation = columnResolver.getColumn();
            String name = columnAnnotation.name();
            String type = columnResolver.getDataType();

            query
                    .append(name)
                    .append(" ")
                    .append(type);

            if (column.isAnnotationPresent(Relation.class)) {
                if (queryReferences.length() != 0) {
                    queryReferences.append(", ");
                }

                TypeResolver relationResolver = columnResolver.getRelatedField();
                Relation relationAnnotation = columnResolver.getRelation();
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
                if (columnResolver.isID()) {
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

}
