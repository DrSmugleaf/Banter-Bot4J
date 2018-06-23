package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 16/03/2018.
 */
public abstract class Model<T extends Model<T>> {

    @SuppressWarnings("unchecked")
    static <T extends Model<T>> void createTable(@Nonnull Class<T> model) {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(model);
        String query = queryBuilder.createTable();

        for (TypeResolver column : getColumns(model)) {
            if (column.FIELD.isAnnotationPresent(Relation.class)) {
                Class<?> relatedModelClass = column.FIELD.getType();
                createTable((Class<T>) relatedModelClass);
            }
        }

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException(e);
        }
    }

    @Nonnull
    static <T extends Model<T>> List<TypeResolver> getColumns(@Nonnull Class<T> model) {
        List<TypeResolver> fields = new ArrayList<>();

        for (Field field : model.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                TypeResolver resolver = new TypeResolver(field);
                fields.add(resolver);
            }
        }

        return fields;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    final Map<TypeResolver, Object> getColumns() {
        Map<TypeResolver, Object> columns = new HashMap<>();
        List<TypeResolver> resolvers = Model.getColumns(getClass());

        for (TypeResolver resolver : resolvers) {
            resolver.FIELD.setAccessible(true);

            Object value;
            try {
                value = resolver.FIELD.get(this);
            } catch (IllegalAccessException e) {
                Database.LOGGER.error("Error getting value from field", e);
                continue;
            }

            columns.put(resolver, value);
        }

        return columns;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private final void createRequirements() {
        for (TypeResolver column : getColumns().keySet()) {
            Field field = column.FIELD;

            if (field.isAnnotationPresent(Relation.class)) {
                Model<T> model = null;
                try {
                    field.setAccessible(true);
                    model = (Model<T>) field.get(this);
                    Class<T> modelClass = (Class<T>) model.getClass().getSuperclass();
                    Method createIfNotExists = modelClass.getDeclaredMethod("createIfNotExists");
                    createIfNotExists.invoke(model);
                } catch (IllegalAccessException e) {
                    throw new InvalidColumnException("Can't access field value for field " + field, e);
                } catch (NoSuchMethodException e) {
                    throw new InvalidColumnException("Column's model class " + model + " doesn't have a createIfNotExists method", e);
                } catch (InvocationTargetException e) {
                    throw new InvalidColumnException("Error executing createIfNotExists method for model " + model, e);
                }
            }
        }
    }

    @Nonnull
    public final List<T> get() {
        List<T> models = new ArrayList<>();
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.get(this);

        Field field = null;
        try (
                PreparedStatement statement = Database.CONNECTION.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                T row = newInstance(this);
                Set<Map.Entry<TypeResolver, Object>> columns = getColumns().entrySet();

                for (Map.Entry<TypeResolver, Object> entry : columns) {
                    TypeResolver column = entry.getKey();
                    Column columnInfo = column.getColumnAnnotation();
                    Object result = resultSet.getObject(columnInfo.name());
                    Object value = column.toValue(result);

                    field = column.FIELD;
                    field.setAccessible(true);
                    field.set(row, value);
                }

                models.add(row);
            }
        } catch (SQLException e) {
            throw new StatementExecutionException("Error executing get statement", e);
        } catch (IllegalAccessException e) {
            throw new StatementExecutionException("Error accessing field " + field, e);
        }

        return models;
    }

    @SuppressWarnings("unchecked")
    public final void createIfNotExists() {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.createIfNotExists(this);

        createRequirements();

        Field field = null;
        try (
                PreparedStatement statement = Database.CONNECTION.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                for (Map.Entry<TypeResolver, Object> entry : getColumns().entrySet()) {
                    TypeResolver column = entry.getKey();
                    Column columnInfo = column.getColumnAnnotation();
                    Object result = resultSet.getObject(columnInfo.name());
                    Object value = column.toValue(result);

                    field = column.FIELD;
                    field.setAccessible(true);
                    field.set(this, value);
                }
            }
        } catch (SQLException sqlException) {
            throw new StatementExecutionException("Error executing createIfNotExists statement", sqlException);
        } catch (IllegalAccessException e) {
            throw new StatementExecutionException("Error accessing field " + field, e);
        }
    }

    public final void save() {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.save(this);

        createRequirements();

        Field field = null;
        try (
                PreparedStatement statement = Database.CONNECTION.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                for (Map.Entry<TypeResolver, Object> entry : getColumns().entrySet()) {
                    TypeResolver column = entry.getKey();
                    Column columnInfo = column.getColumnAnnotation();
                    Object result = resultSet.getObject(columnInfo.name());
                    Object value = column.toValue(result);

                    field = column.FIELD;
                    field.setAccessible(true);
                    field.set(this, value);
                }
            }
        } catch (SQLException e) {
            throw new StatementExecutionException("Error executing save statement", e);
        } catch (IllegalAccessException e) {
            throw new StatementExecutionException("Error accessing field " + field, e);
        }
    }

    public final void delete() {
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);
        String query = queryBuilder.delete(this);

        try (PreparedStatement statement = Database.CONNECTION.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException("Error executing delete statement", e);
        }
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    static <T extends Model<T>> T newInstance(@Nonnull Class<?> model) {
        try {
            Constructor<T> constructor = (Constructor<T>) model.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new ModelInstantiationException("Error creating new model of class " + model, e);
        }
    }

    @Nonnull
    static <T extends Model<T>> T newInstance(@Nonnull Model<T> model) {
        return newInstance(model.getClass());
    }

}
