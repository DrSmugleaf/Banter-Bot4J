package com.github.drsmugleaf.database.api.types;

import com.github.drsmugleaf.database.api.InvalidColumnException;
import com.github.drsmugleaf.database.api.TypeResolver;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 06/04/2018.
 */
public interface SQLTypes {

    @Nullable
    static <T extends Enum<T> & SQLTypes> String getType(Class<T> types, @Nonnull TypeResolver column) {
        Class<?> columnType = column.FIELD.getType();
        if (column.isAutoIncremented()) {
            if (columnType == Integer.class) {
                return "SERIAL";
            } else if (columnType == Long.class) {
                return "BIGSERIAL";
            } else {
                throw new InvalidColumnException("Auto incremented column " + column.FIELD + " is neither an Integer nor a Long");
            }
        }

        for (T type : types.getEnumConstants()) {
            if (type.getJavaType() == String.class && columnType.isEnum()) {
                return type.getName();
            } else if (type.getJavaType() == columnType) {
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
