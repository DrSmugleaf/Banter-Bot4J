package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;

import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 16/03/2018.
 */
public abstract class Model<T extends Model<T>> {

    @SuppressWarnings("unchecked")
    static <T extends Model<T>> void createTable(@NotNull Class<T> model) {
        for (TypeResolver column : getColumns(model)) {
            if (column.FIELD.isAnnotationPresent(Relation.class)) {
                Class<?> relatedModelClass = column.FIELD.getType();
                createTable((Class<T>) relatedModelClass);
            }
        }

        QueryBuilder<T> queryBuilder = new QueryBuilder<>(model);
        try (
                Query query = queryBuilder.createTable()
        ) {
            query.STATEMENT.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException(e);
        }
    }

    @NotNull
    static <T extends Model<T>> List<TypeResolver> getColumns(@NotNull Class<T> model) {
        List<TypeResolver> fields = new ArrayList<>();

        for (Field field : model.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                TypeResolver resolver = new TypeResolver(field);
                fields.add(resolver);
            }
        }

        return fields;
    }

    @NotNull
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

    @NotNull
    public final List<T> get() {
        List<T> models = new ArrayList<>();
        QueryBuilder<T> queryBuilder = new QueryBuilder<>(this);

        Field field = null;
        try (
                Query query = queryBuilder.get(this);
                ResultSet resultSet = query.STATEMENT.executeQuery()
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

        createRequirements();

        Field field = null;
        try (
                Query query = queryBuilder.createIfNotExists(this);
                ResultSet resultSet = query.STATEMENT.executeQuery()
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

        createRequirements();

        Field field = null;
        try (
                Query query = queryBuilder.save(this);
                ResultSet resultSet = query.STATEMENT.executeQuery()
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

        try (Query query = queryBuilder.delete(this)) {
            query.STATEMENT.executeUpdate();
        } catch (SQLException e) {
            throw new StatementExecutionException("Error executing delete statement", e);
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    static <T extends Model<T>> T newInstance(@NotNull Class<?> model) {
        try {
            Constructor<T> constructor = (Constructor<T>) model.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new ModelInstantiationException("Error creating new model of class " + model, e);
        }
    }

    @NotNull
    static <T extends Model<T>> T newInstance(@NotNull Model<T> model) {
        return newInstance(model.getClass());
    }

}
