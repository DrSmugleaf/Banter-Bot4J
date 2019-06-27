package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.Table;
import com.github.drsmugleaf.database.api.types.PostgresTypes;
import com.github.drsmugleaf.database.api.types.SQLTypes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
public class TypeResolver {

    public final Field FIELD;

    TypeResolver(Field type) {
        FIELD = type;
    }

    private static TypeResolver getDeepRelatedField(Field field) {
        Relation relation = field.getDeclaredAnnotation(Relation.class);
        String relatedColumnName = relation.columnName();

        Field relatedField;
        try {
            relatedField = field.getType().getDeclaredField(relatedColumnName);
        } catch (NoSuchFieldException e) {
            throw new InvalidColumnException(
                    "Field " + field + " doesn't have a related field named " +
                    relatedColumnName + " in class " + field.getType()
            );
        }

        if (relatedField.isAnnotationPresent(Relation.class)) {
            return getDeepRelatedField(relatedField);
        }

        return new TypeResolver(relatedField);
    }

    private <T extends Annotation> T getAnnotation(Class<T> annotation) {
        if (!FIELD.isAnnotationPresent(annotation)) {
            throw new NullPointerException("Field " + FIELD + " doesn't have a " + annotation.getName() + " annotation");
        }

        return FIELD.getDeclaredAnnotation(annotation);
    }

    public boolean isID() {
        return FIELD.getDeclaredAnnotation(Column.Id.class) != null;
    }

    public boolean isAutoIncremented() {
        return FIELD.getDeclaredAnnotation(Column.AutoIncrement.class) != null;
    }

    String getDataType() {
        Column columnAnnotation = getColumnAnnotation();

        TypeResolver column = this;
        String columnDefinition = columnAnnotation.columnDefinition();
        Relation relation = FIELD.getDeclaredAnnotation(Relation.class);
        if (relation != null) {
            column = getDeepRelatedField(FIELD);
            Column relatedColumnAnnotation = column.getColumnAnnotation();
            columnDefinition = relatedColumnAnnotation.columnDefinition();
        }

        if (columnDefinition.isEmpty()) {
            String sqlType = SQLTypes.getType(PostgresTypes.class, column);
            if (sqlType == null) {
                throw new InvalidColumnException("No equivalent SQL type exists for column " + column);
            }

            return sqlType;
        } else {
            return columnDefinition;
        }
    }

    Table getTableAnnotation() {
        Class<?> type = FIELD.getDeclaringClass();
        if (!type.isAnnotationPresent(Table.class)) {
            throw new NullPointerException();
        }

        return type.getDeclaredAnnotation(Table.class);
    }

    Column getColumnAnnotation() {
        return getAnnotation(Column.class);
    }

    Relation getRelationAnnotation() {
        return getAnnotation(Relation.class);
    }

    TypeResolver getRelatedField() {
        Relation relation = getRelationAnnotation();
        String relatedColumnName = relation.columnName();

        Field field;
        try {
            field = FIELD.getType().getDeclaredField(relatedColumnName);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("No related field found with name " + relatedColumnName + " for field " + FIELD);
        }

        return new TypeResolver(field);
    }

    String getExternalColumnName() {
        Column columnAnnotation = getColumnAnnotation();
        Table tableAnnotation = FIELD.getDeclaredAnnotation(Table.class);

        if (tableAnnotation == null) {
            return columnAnnotation.name();
        } else {
            return tableAnnotation.name() + "." + columnAnnotation.name();
        }
    }

    @Nullable
    Object toSQL(@Nullable Object object) {
        if (object == null) {
            return null;
        }

        if (FIELD.getType().isEnum()) {
            return object.toString();
        }

        if (!FIELD.isAnnotationPresent(Relation.class)) {
            return object;
        }

        Relation relation = getRelationAnnotation();
        Field objectField;
        try {
            objectField = object.getClass().getDeclaredField(relation.columnName());
        } catch (NoSuchFieldException e) {
            throw new ModelException("No field found in " + object.getClass().getSimpleName() + " with name " + relation.columnName(), e);
        }

        objectField.setAccessible(true);
        try {
            return objectField.get(object);
        } catch (IllegalAccessException e) {
            throw new ModelException("Error accessing value of field " + objectField.getName() + " in object " + object.getClass().getSimpleName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Enum<T>> Enum<T> toEnum(Class<?> type, Object object) {
        return Enum.valueOf((Class<T>) type, object.toString());
    }

    private Collection<?> toCollection(Class<?> type) {
        try {
            return (Collection<?>) type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ModelException("Error creating collection from class " + type.getSimpleName());
        }
    }

    private Map<?, ?> toMap(Class<?> type) {
        try {
            return (Map<?, ?>) type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ModelException("Error creating map from class " + type.getSimpleName());
        }
    }

    @Nullable
    <T> Object toValue(@Nullable T result) {
        if (result == null) {
            return null;
        }

        Class<?> type = FIELD.getType();

        if (type.isEnum()) {
            return toEnum(type, result);
        } else if (FIELD.isAnnotationPresent(Relation.class)) {
            Model<?> model;
            model = Model.newInstance(type);

            Field relatedField = getRelatedField().FIELD;
            relatedField.setAccessible(true);
            try {
                relatedField.set(model, result);
            } catch (IllegalAccessException e) {
                throw new ModelException("Error setting value for field " + relatedField.getName() + " in class " + model.getClass().getSimpleName(), e);
            }

            List<?> results = model.get();
            if (results.size() > 1) {
                if (Collection.class.isAssignableFrom(type) || List.class.isAssignableFrom(type)) {
                    return results;
                } else {
                    throw new ModelException("Retrieved more than 1 result without field being a collection type");
                }
            } else {
                return results.get(0);
            }
        } else {
            return result;
        }
    }

    String getColumnDefinition() {
        StringBuilder definition = new StringBuilder();
        Column column = getColumnAnnotation();
        String dataType = getDataType();

        dataType = dataType
                .replaceAll("\\$l", String.valueOf(column.length()))
                .replaceAll("\\$p", String.valueOf(column.precision()))
                .replaceAll("\\$s", String.valueOf(column.scale()));

        definition
                .append(column.name())
                .append(" ")
                .append(dataType);

        if (FIELD.isAnnotationPresent(Relation.class)) {
            TypeResolver relationResolver = getRelatedField();
            Relation relation = getRelationAnnotation();
            String relatedTableName = relationResolver.getTableAnnotation().name();

            definition
                    .append(" REFERENCES ")
                    .append(relatedTableName)
                    .append(" (")
                    .append(relation.columnName())
                    .append(") ON UPDATE CASCADE ON DELETE CASCADE ");
        }

        String defaultValue = column.defaultValue();
        if (!defaultValue.isEmpty()) {
            definition
                    .append(" DEFAULT ")
                    .append(defaultValue);
        }

        return definition.toString();
    }

}
