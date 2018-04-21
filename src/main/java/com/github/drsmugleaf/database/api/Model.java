package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 16/03/2018.
 */
public abstract class Model<T extends Model<T>> {

    static <T extends Model<T>> void createTable(@Nonnull Class<T> model) throws SQLException, InvalidColumnException {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(model);
        PreparedStatement statement = Database.CONNECTION.prepareStatement(queryBuilder.createTable());
        statement.executeUpdate();
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
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);

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
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
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
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.save(this);
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

    public final void delete() throws ModelException {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.delete(this);
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

    @Nonnull
    @SuppressWarnings("unchecked")
    static <T extends Model<T>> T newInstance(@Nonnull Class<?> model) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<T> constructor = (Constructor<T>) model.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    @Nonnull
    static <T extends Model<T>> T newInstance(@Nonnull Model<T> model) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return newInstance(model.getClass());
    }

}
