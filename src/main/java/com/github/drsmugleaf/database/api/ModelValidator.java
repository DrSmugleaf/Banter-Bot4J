package com.github.drsmugleaf.database.api;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 24/04/2018.
 */
public enum ModelValidator {

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
    };

    abstract <T extends Model<T>> void validate(Class<T> model);

    public static <T extends Model<T>> void validateAll(Class<T> model) {
        for (ModelValidator validator : values()) {
            validator.validate(model);
        }
    }

}
