package com.github.drsmugleaf.database.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 06/04/2018.
 */
public interface Types {

    @Nullable
    static <T extends Enum<T> & Types> T getType(Class<T> types, @Nonnull Class<?> javaType) {
        for (T type : types.getEnumConstants()) {
            if (type.getJavaType() == javaType) {
                return type;
            }
        }

        return null;
    }

    @Nonnull
    String getName();

    @Nonnull
    Class<?> getJavaType();

}
