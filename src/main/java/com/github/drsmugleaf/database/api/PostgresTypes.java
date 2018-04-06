package com.github.drsmugleaf.database.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by DrSmugleaf on 30/03/2018.
 */
public enum PostgresTypes {

    BIGINT("int8", Long.class),

    BINARY("bytea", Byte[].class),
    LONGVARBINARY("bytea", Byte[].class),
    VARBINARY("bytea", Byte[].class),

    BIT("bool", Boolean.class),

    BLOB("oid", Blob.class),

    CHAR("char(1)", Character.class),

    CLOB("text", Clob.class),

    DATE("date", Date.class),

    DOUBLE("float8", Double.class),

    FLOAT("float4", Float.class),

    INTEGER("int4", Integer.class),

    LONGVARCHAR("text", String.class),
    VARCHAR("varchar($l)", String.class),

    NUMERIC("numeric($p, $s)", BigDecimal.class),

    SMALLINT("int2", Short.class),

    TIME("time", Time.class),

    TIMESTAMP("timestamp", Timestamp.class),

    TINYINT("int2", Byte.class);

    @Nonnull
    public final String NAME;

    @Nonnull
    public final Class<?> CLASS;

    PostgresTypes(@Nonnull String name, @Nonnull Class<?> clazz) {
        NAME = name;
        CLASS = clazz;
        Holder.MAP.put(clazz, this);
    }

    @Nullable
    public static PostgresTypes getType(@Nonnull Class<?> clazz) {
        if (!Holder.MAP.containsKey(clazz)) {
            return null;
        }

        return Holder.MAP.get(clazz).iterator().next();
    }

    private static class Holder {
        static final Multimap<Class<?>, PostgresTypes> MAP = ArrayListMultimap.create();
    }

}
