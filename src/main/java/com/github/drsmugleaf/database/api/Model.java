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
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 16/03/2018.
 */
public abstract class Model<T extends Model<T>> {

    static <T extends Model<T>> void createTable(@Nonnull Class<T> model) throws InvalidColumnException {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(model);
        String query = queryBuilder.createTable();

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException(e);
        }
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
    public final List<T> get() {
        List<T> models = new ArrayList<>();
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.get(this);

        try (
                PreparedStatement statement = Database.CONNECTION.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                T row = newInstance(this);
                Set<Map.Entry<TypeResolver, Object>> columns = queryBuilder.getColumns(this).entrySet();

                for (Map.Entry<TypeResolver, Object> column : columns) {
                    TypeResolver resolver = column.getKey();
                    Column columnAnnotation = resolver.getColumn();
                    Object result = resultSet.getObject(columnAnnotation.name());
                    Object value = resolver.toValue(result);

                    resolver.FIELD.set(row, value);
                }

                models.add(row);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new StatementExecutionException(e);
        }

        return models;
    }

    public final void createIfNotExists() {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.createIfNotExists(this);

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException(e);
        }
    }

    public final void save() {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.save(this);

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException(e);
        }
    }

    public final void delete() {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.delete(this);

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException(e);
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
