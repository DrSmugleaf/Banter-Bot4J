package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 16/03/2018.
 */
public abstract class Model<T extends Model<T>> {

    static <T extends Model> void createTable(@Nonnull Class<T> model) throws SQLException, InvalidColumnException {
        StringBuilder query = new StringBuilder();
        StringBuilder queryConstraintKey = new StringBuilder();
        StringBuilder queryConstraintValue = new StringBuilder();

        query
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(escape(getTableName(model)))
                .append(" (");

        List<Field> columns = getColumns(model);
        Iterator<Field> iterator = columns.iterator();
        while (iterator.hasNext()) {
            Field column = iterator.next();
            Column columnAnnotation = column.getDeclaredAnnotation(Column.class);
            String name = columnAnnotation.name();
            String type = getDataType(column);

            query
                    .append(name)
                    .append(" ")
                    .append(type);

            if (column.isAnnotationPresent(Relation.class)) {
                if (queryConstraintValue.length() != 0) {
                    queryConstraintValue.append(", ");
                }
                Relation relationAnnotation = column.getDeclaredAnnotation(Relation.class);
                Class<?> referencedClass = column.getType();
                Table referencedTable = referencedClass.getDeclaredAnnotation(Table.class);

                query
                        .append(" REFERENCES ")
                        .append(referencedTable.name())
                        .append(" (")
                        .append(relationAnnotation.columnName())
                        .append(") ON UPDATE CASCADE ON DELETE CASCADE, ");

                queryConstraintKey
                        .append(referencedTable.name())
                        .append("_");

                queryConstraintValue.append(columnAnnotation.name());
            } else {
                if (column.isAnnotationPresent(Column.Id.class)) {
                    query.append(" PRIMARY KEY ");
                }

                String defaultValue = columnAnnotation.defaultValue();
                if (!defaultValue.isEmpty()) {
                    query
                            .append(" DEFAULT ")
                            .append(defaultValue);
                }

                if (iterator.hasNext() || queryConstraintKey.length() != 0) {
                    query.append(", ");
                }
            }
        }

        if (queryConstraintKey.length() != 0) {
            queryConstraintKey.append("pkey PRIMARY KEY ");
            queryConstraintValue.append(")");
            queryConstraintKey.insert(0, "CONSTRAINT ");
            queryConstraintValue.insert(0, "(");
        }

        query
                .append(queryConstraintKey)
                .append(queryConstraintValue)
                .append(")");

        PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString());
        statement.executeUpdate();
    }

    @Nonnull
    private static <T extends Model> String getTableName(@Nonnull Class<T> model) {
        Table tableAnnotation = model.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalArgumentException("Model " + model.getName() + " doesn't have a " + Table.class.getName() + " annotation");
        }

        return tableAnnotation.name();
    }

    @Nonnull
    private static <T extends Model> List<Field> getColumns(@Nonnull Class<T> model) {
        List<Field> fields = new ArrayList<>();

        for (Field field : model.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                fields.add(field);
            }
        }

        return fields;
    }

    @Nonnull
    private static String getColumnName(@Nonnull Field field) {
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
    private static String escape(@Nonnull String s) throws ModelException {
        try {
            PreparedStatement statement = Database.CONNECTION.prepareStatement("?");
            statement.setString(1, s);
            return statement.toString().replaceFirst("'(.+)'", "$1");
        } catch (SQLException e) {
            throw new ModelException(e);
        }
    }

    private static boolean isID(@Nonnull Field field) {
        return field.isAnnotationPresent(Column.Id.class);
    }

    @Nonnull
    private static String getDataType(@Nonnull Field field) throws InvalidColumnException {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation == null) {
            throw new NullPointerException("No column annotation found for field " + field);
        }

        String columnDefinition = field.getAnnotation(Column.class).columnDefinition();
        if (field.isAnnotationPresent(Relation.class)) {
            Relation relation = field.getDeclaredAnnotation(Relation.class);
            Field relatedField;

            try {
                relatedField = field.getType().getDeclaredField(relation.columnName());
            } catch (NoSuchFieldException e) {
                throw new ModelException(e);
            }

            columnDefinition = relatedField.getDeclaredAnnotation(Column.class).columnDefinition();
            if (columnDefinition.isEmpty()) {
                field = relatedField;
            }
        }

        if (columnDefinition.isEmpty()) {
            Class<?> fieldType = field.getType();
            Types type = Types.getType(PostgresTypes.class, fieldType);

            if (field.getType().isEnum()) {
                type = Types.getType(PostgresTypes.class, String.class);
            }

            if (type == null) {
                throw new InvalidColumnException("No type exists for class " + fieldType.getName());
            }

            return type.getName();
        } else {
            return columnDefinition;
        }
    }

    @Nullable
    private static Object resolveValue(@Nonnull Map.Entry<Field, Object> entry) {
        Field field = entry.getKey();
        Object object = entry.getValue();

        if (field.getType().isEnum()) {
            return object.toString();
        }

        if (!field.isAnnotationPresent(Relation.class)) {
            return object;
        }

        Relation relation = field.getDeclaredAnnotation(Relation.class);
        Field objectField = null;
        for (Field classField : object.getClass().getDeclaredFields()) {
            if (Objects.equals(classField.getName(), relation.columnName())) {
                objectField = classField;
                break;
            }
        }

        try {
            objectField.setAccessible(true);
            return objectField.get(object);
        } catch (IllegalAccessException e) {
            throw new ModelException(e);
        }
    }

    static <T extends Model> void validate(@Nonnull Class<T> model) {
        for (Field field : getColumns(model)) {
            if (field.getType().isPrimitive()) {
                throw new ModelException("Field " + field + " in model " + model.toString() + " is primitive");
            }
        }

        if (Stream.of(model.getDeclaredConstructors()).noneMatch(c -> c.getParameterCount() == 0)) {
            throw new ModelException("Model " + model.toString() + " doesn't have a constructor with no arguments");
        }
    }

    @Nonnull
    public final List<T> get() throws ModelException {
        List<T> models = new ArrayList<>();

        PreparedStatement statement;
        StringBuilder query = new StringBuilder();
        query
                .append("SELECT * FROM ")
                .append(escape(getTableName(this.getClass())));

        Set<Map.Entry<Field, Object>> fieldEntries = getFields(this).entrySet();
        fieldEntries.removeIf(entry -> entry.getValue() == null);
        if (!fieldEntries.isEmpty()) {
            query.append(" WHERE ");
        }

        Iterator<Map.Entry<Field, Object>> iterator = fieldEntries.iterator();
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
            statement = Database.CONNECTION.prepareStatement(query.toString());

            int i = 1;
            for (Map.Entry<Field, Object> column : fieldEntries) {
                statement.setObject(i, resolveValue(column));
                i++;
            }

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                T row = newInstance(this);
                for (Map.Entry<Field, Object> entry : getFields(this).entrySet()) {
                    Field field = entry.getKey();
                    Object object = entry.getValue();
                    Column columnAnnotation = field.getAnnotation(Column.class);

                    if (field.getType().isEnum()) {
                        for (Object o : field.getType().getEnumConstants()) {
                            if (Objects.equals(o.toString(), result.getString(columnAnnotation.name()))) {
                                object = o;
                            }
                        }
                    } else if (field.isAnnotationPresent(Relation.class)) {
                        Relation relationAnnotation = field.getDeclaredAnnotation(Relation.class);
                        Model<?> model = newInstance(field);
                        Field objectField = field.getType().getDeclaredField(relationAnnotation.columnName());
                        objectField.setAccessible(true);
                        objectField.set(model, result.getObject(columnAnnotation.name()));
                        object = model.get().get(0);
                    } else {
                        object = result.getObject(columnAnnotation.name());
                    }

                    field.set(row, object);
                }
                models.add(row);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
            throw new ModelException(e);
        }

        return models;
    }

    public final void createIfNotExists() throws ModelException {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();

        queryInsert
                .append("INSERT INTO ")
                .append(escape(getTableName(this.getClass())))
                .append(" (");
        queryValues.append("VALUES(");
        queryConflict.append("ON CONFLICT DO NOTHING");

        Set<Map.Entry<Field, Object>> fields = getFields(this).entrySet();
        Iterator<Map.Entry<Field, Object>> iterator = fields.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Field, Object> entry = iterator.next();
            Field field = entry.getKey();
            String columnName = getColumnName(field);

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

        try {
            PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString());
            iterator = fields.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                Map.Entry<Field, Object> entry = iterator.next();
                statement.setObject(i, resolveValue(entry));
                i++;
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException(e);
        }
    }

    public final void save() throws ModelException {
        StringBuilder query = new StringBuilder();
        StringBuilder queryInsert = new StringBuilder();
        StringBuilder queryValues = new StringBuilder();
        StringBuilder queryConflict = new StringBuilder();
        StringBuilder querySet = new StringBuilder();

        queryInsert
                .append("INSERT INTO ")
                .append(escape(getTableName(this.getClass())))
                .append(" (");
        queryValues.append("VALUES(");
        queryConflict.append("ON CONFLICT (");
        querySet.append("DO UPDATE SET ");

        Set<Map.Entry<Field, Object>> fields = getFields(this).entrySet();
        Iterator<Map.Entry<Field, Object>> iterator = fields.iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next().getKey();
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

        try {
            PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString());
            iterator = fields.iterator();
            int i = 1;
            int size = fields.size();
            while (iterator.hasNext()) {
                Map.Entry<Field, Object> entry = iterator.next();
                Object value = resolveValue(entry);
                statement.setObject(i, value);
                statement.setObject(i + size, value);
                i++;
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException(e);
        }
    }

    public final void delete() throws ModelException {
        StringBuilder query = new StringBuilder();

        query
                .append("DELETE FROM ")
                .append(escape(getTableName(this.getClass())));

        Set<Map.Entry<Field, Object>> fields = getFields(this).entrySet();
        fields.removeIf(entry -> entry.getValue() == null);
        if (!fields.isEmpty()) {
            query.append(" WHERE ");

            Iterator<Map.Entry<Field, Object>> iterator = fields.iterator();
            while (iterator.hasNext()) {
                Map.Entry<Field, Object> entry = iterator.next();
                String columnName = getColumnName(entry.getKey());

                query
                        .append(" (")
                        .append(columnName)
                        .append(" = ?")
                        .append(") ");

                if (iterator.hasNext()) {
                    query.append(" OR ");
                }
            }
        }

        try {
            PreparedStatement statement = Database.CONNECTION.prepareStatement(query.toString());

            int i = 1;
            for (Map.Entry<Field, Object> entry : fields) {
                statement.setObject(i, resolveValue(entry));
                i++;
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException(e);
        }
    }

    @Nonnull
    private Map<Field, Object> getFields(@Nonnull Model<T> model) {
        Map<Field, Object> fields = new HashMap<>();

        for (Field column : getColumns(model.getClass())) {
            column.setAccessible(true);

            Object value;
            try {
                value = column.get(model);
            } catch (IllegalAccessException e) {
                BanterBot4J.LOGGER.error("Error getting value from field", e);
                continue;
            }

            fields.put(column, value);
        }

        return fields;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private static <T extends Model<T>> T newInstance(@Nonnull Class<T> model) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<? extends Model> constructor = model.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (T) constructor.newInstance();
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private static <T extends Model<T>> T newInstance(@Nonnull Model<T> model) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return newInstance((Class<T>) model.getClass());
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private static <T extends Model<T>> T newInstance(@Nonnull Field field) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return newInstance((Class<T>) field.getType());
    }

}
