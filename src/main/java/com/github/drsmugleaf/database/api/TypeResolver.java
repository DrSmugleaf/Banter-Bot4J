package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class TypeResolver {

    @Nonnull
    public final Field FIELD;

    TypeResolver(@Nonnull Field type) {
        FIELD = type;
    }

    @Nonnull
    private static Field getDeepRelatedField(@Nonnull Field field) throws InvalidColumnException {
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

        return relatedField;
    }

    @Nonnull
    private <T extends Annotation> T getAnnotation(Class<T> annotation) {
        if (!FIELD.isAnnotationPresent(annotation)) {
            throw new NullPointerException("Field " + FIELD + " doesn't have a " + annotation.getName() + " annotation");
        }

        return FIELD.getDeclaredAnnotation(annotation);
    }

    boolean isID() {
        return FIELD.getDeclaredAnnotation(Column.Id.class) != null;
    }

    @Nonnull
    String getDataType() throws InvalidColumnException {
        Column columnAnnotation = getColumn();

        Field field;
        String columnDefinition;
        Relation relation = FIELD.getDeclaredAnnotation(Relation.class);
        if (relation != null) {
            field = getDeepRelatedField(FIELD);
            Column relatedColumnAnnotation = field.getDeclaredAnnotation(Column.class);
            columnDefinition = relatedColumnAnnotation.columnDefinition();
        } else {
            field = FIELD;
            columnDefinition = columnAnnotation.columnDefinition();
        }

        if (columnDefinition.isEmpty()) {
            Class<?> fieldType = field.getType();
            Types type;

            if (fieldType.isEnum()) {
                type = Types.getType(PostgresTypes.class, String.class);
            } else {
                type = Types.getType(PostgresTypes.class, fieldType);
            }

            if (type == null) {
                throw new InvalidColumnException("No equivalent SQL type exists for field " + field.getName());
            }

            return type.getName();
        } else {
            return columnDefinition;
        }
    }

    @Nonnull
    Table getTable() {
        Class<?> type = FIELD.getDeclaringClass();
        if (!type.isAnnotationPresent(Table.class)) {
            throw new NullPointerException();
        }

        return type.getDeclaredAnnotation(Table.class);
    }

    @Nonnull
    Column getColumn() {
        return getAnnotation(Column.class);
    }

    @Nonnull
    Relation getRelation() {
        return getAnnotation(Relation.class);
    }

    @Nonnull
    TypeResolver getRelatedField() {
        Relation relation = getRelation();
        String relatedColumnName = relation.columnName();

        Field field;
        try {
            field = FIELD.getType().getDeclaredField(relatedColumnName);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Field " + FIELD + " doesn't have a related field with name " + relatedColumnName);
        }

        return new TypeResolver(field);
    }

    @Nonnull
    String getColumnName() {
        Column columnAnnotation = getColumn();
        Table tableAnnotation = FIELD.getDeclaredAnnotation(Table.class);

        if (tableAnnotation == null) {
            return columnAnnotation.name();
        } else {
            return tableAnnotation.name() + "." + columnAnnotation.name();
        }
    }

    @Nullable
    Object resolveValue(@Nullable Object object) {
        if (object == null) {
            return null;
        }

        if (FIELD.getType().isEnum()) {
            return object.toString();
        }

        if (!FIELD.isAnnotationPresent(Relation.class)) {
            return object;
        }

        Relation relation = getRelation();
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

}
