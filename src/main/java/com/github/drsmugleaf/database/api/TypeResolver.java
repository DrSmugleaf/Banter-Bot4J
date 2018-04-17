package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by DrSmugleaf on 12/04/2018.
 */
class TypeResolver {

    @Nonnull
    private final Field FIELD;

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
            throw new NullPointerException();
        }

        return FIELD.getDeclaredAnnotation(annotation);
    }

    boolean isID() {
        return FIELD.getDeclaredAnnotation(Column.Id.class) != null;
    }

    @Nonnull
    String getDataType() throws InvalidColumnException {
        Column columnAnnotation = FIELD.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null) {
            throw new IllegalStateException("Field " + FIELD + " doesn't have a " + Column.class.getName() + " annotation");
        }

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

        return type.getAnnotation(Table.class);
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
        Relation relation = FIELD.getDeclaredAnnotation(Relation.class);
        String relatedColumnName = relation.columnName();

        Field field;
        try {
            field = FIELD.getType().getDeclaredField(relatedColumnName);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Field " + FIELD + " doesn't have a related field with name " + relatedColumnName);
        }

        return new TypeResolver(field);
    }

}
