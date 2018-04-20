package com.github.drsmugleaf.database.api;

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
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(model);
        PreparedStatement statement = Database.CONNECTION.prepareStatement(queryBuilder.createTable());
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
    @SuppressWarnings("unchecked")
    public final List<T> get() throws ModelException {
        List<T> models = new ArrayList<>();
        QueryBuilder<T> queryBuilder = new QueryBuilder<>((T) this);

        try {
            PreparedStatement statement = Database.CONNECTION.prepareStatement(queryBuilder.get(this));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                T row = newInstance(this);
                for (Map.Entry<TypeResolver, Object> entry : queryBuilder.getColumns(this).entrySet()) {
                    TypeResolver resolver = entry.getKey();
                    Column columnAnnotation = resolver.getColumn();
                    Object result = resultSet.getObject(columnAnnotation.name());
                    Object value = resolver.toValue(result);

                    resolver.FIELD.set(row, value);
                }
                models.add(row);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new ModelException(e);
        }

        return models;
    }

    public final void createIfNotExists() throws ModelException {
        QueryBuilder queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.createIfNotExists(this);
        PreparedStatement statement;

        try {
            statement = Database.CONNECTION.prepareStatement(query);
        } catch (SQLException e) {
            throw new ModelException("Error creating SQL query", e);
        }

        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Error executing SQL statement", e);
        }
    }

    public final void save() throws ModelException {
        QueryBuilder queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.save(this);
        PreparedStatement statement;

        try {
            statement = Database.CONNECTION.prepareStatement(query.toString());
        } catch (SQLException e) {
            throw new ModelException("Error creating SQL query", e);
        }

        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException("Error executing SQL statement", e);
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
                Database.LOGGER.error("Error getting value from field", e);
                continue;
            }

            fields.put(column, value);
        }

        return fields;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    static <T extends Model<T>> T newInstance(@Nonnull Class<?> model) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<T> constructor = (Constructor<T>) model.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    static <T extends Model<T>> T newInstance(@Nonnull Model<T> model) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return newInstance(model.getClass());
    }

}
