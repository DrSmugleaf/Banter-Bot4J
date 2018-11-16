package com.github.drsmugleaf.database.api;

import org.jetbrains.annotations.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 24/06/2018
 */
public class Query implements AutoCloseable {

    final PreparedStatement STATEMENT;

    Query(@NotNull PreparedStatement statement) {
        STATEMENT = statement;
    }

    @Override
    public void close() throws SQLException {
        STATEMENT.close();
        STATEMENT.getConnection().close();
    }

}
