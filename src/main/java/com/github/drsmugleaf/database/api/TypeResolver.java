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
    private final Field FIELD;

    TypeResolver(@Nonnull Field type) {
        FIELD = type;
    }

    @Nonnull
    private static Field getRelatedField(@Nonnull Field field) throws InvalidColumnException {
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
            return getRelatedField(relatedField);
        }

        return relatedField;
    }

    @Nullable
    private <T extends Annotation> T getAnnotation(Class<T> annotation) {
        if (FIELD.isAnnotationPresent(annotation)) {
            return FIELD.getDeclaredAnnotation(annotation);
        }

        return null;
    }

    public boolean isID() {
        return getAnnotation(Column.Id.class) != null;
    }

    @Nullable
    public String getDataType() throws InvalidColumnException {
        Column columnAnnotation = getAnnotation(Column.class);
        if (columnAnnotation == null) {
            throw new IllegalStateException("Field " + FIELD + " doesn't have a " + Column.class.getName() + " annotation");
        }

        Field field;
        String columnDefinition;
        Relation relation = getAnnotation(Relation.class);
        if (relation != null) {
            field = getRelatedField(FIELD);
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

}
