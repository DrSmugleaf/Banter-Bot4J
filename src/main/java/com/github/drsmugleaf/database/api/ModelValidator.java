package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.types.PostgresTypes;
import com.github.drsmugleaf.database.api.types.SQLTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 24/04/2018.
 */
enum ModelValidator {

    PRIMITIVE_ONLY {
        @Override
        <T extends Model<T>> void validate(Class<T> model) {
            List<TypeResolver> columns = Model.getColumns(model);

            for (TypeResolver typeResolver : columns) {
                if (typeResolver.FIELD.getType().isPrimitive()) {
                    throw new ValidationException("Field " + typeResolver.FIELD + " in model " + model + " is primitive");
                }
            }
        }
    },
    EMPTY_CONSTRUCTOR {
        @Override
        <T extends Model<T>> void validate(Class<T> model) {
            Stream<Constructor<?>> declaredConstructors = Stream.of(model.getDeclaredConstructors());
            if (declaredConstructors.noneMatch(c -> c.getParameterCount() == 0)) {
                throw new ValidationException("Model " + model + " doesn't have a constructor with no arguments");
            }
        }
    },
    RELATED_FIELD_EXISTS {
        @Override
        <T extends Model<T>> void validate(Class<T> model) {
            for (TypeResolver resolver : Model.getColumns(model)) {
                if (!resolver.FIELD.isAnnotationPresent(Relation.class)) {
                    continue;
                }

                Relation relation = resolver.getRelation();
                String relatedColumnName = relation.columnName();

                Field field = resolver.FIELD;
                try {
                    field.getType().getDeclaredField(relatedColumnName);
                } catch (NoSuchFieldException e) {
                    throw new ValidationException("No related field found with name " + relatedColumnName + " for field " + field);
                }
            }
        }
    },
    TYPE_EXISTS {
        @Override
        <T extends Model<T>> void validate(Class<T> model) {
            for (TypeResolver resolver : Model.getColumns(model)) {
                Field field = resolver.FIELD;
                if (field.isAnnotationPresent(Relation.class)) {
                    continue;
                }

                String sqlType = SQLTypes.getType(PostgresTypes.class, field);
                if (sqlType == null) {
                    throw new ValidationException("No equivalent SQL type exists for field " + field);
                }
            }
        }
    };

    abstract <T extends Model<T>> void validate(Class<T> model);

    public static <T extends Model<T>> void validateAll(Class<T> model) {
        for (ModelValidator validator : values()) {
            validator.validate(model);
        }
    }

}
