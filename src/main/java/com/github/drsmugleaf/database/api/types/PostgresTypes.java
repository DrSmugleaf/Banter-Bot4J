package com.github.drsmugleaf.database.api.types;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by DrSmugleaf on 30/03/2018.
 */
public enum PostgresTypes implements SQLTypes {

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

    private final String NAME;
    private final Class<?> CLASS;

    PostgresTypes(String name, Class<?> clazz) {
        NAME = name;
        CLASS = clazz;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Class<?> getJavaType() {
        return CLASS;
    }

}
