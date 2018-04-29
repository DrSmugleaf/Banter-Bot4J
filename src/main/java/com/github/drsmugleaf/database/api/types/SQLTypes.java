package com.github.drsmugleaf.database.api.types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

/**
 * Created by DrSmugleaf on 06/04/2018.
 */
public interface SQLTypes {

    @Nullable
    static <T extends Enum<T> & SQLTypes> String getType(Class<T> types, @Nonnull Field column) {
        for (T type : types.getEnumConstants()) {
            if (type.getJavaType() == String.class && column.getType().isEnum()) {
                return type.getName();
            } else if (type.getJavaType() == column.getType()) {
                return type.getName();
            }
        }

        return null;
    }

    @Nonnull
    String getName();

    @Nonnull
    Class<?> getJavaType();

}
