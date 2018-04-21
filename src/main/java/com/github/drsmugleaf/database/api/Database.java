package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
@SuppressWarnings("unchecked")
public class Database {

    @Nonnull
    static final Logger LOGGER = initLogger();

    @Nonnull
    static final Connection CONNECTION = DatabaseConnection.initialize();

    static {
        Reflection reflection = new Reflection("com.github.drsmugleaf.database.models");
        List<Class<? extends Model>> models = reflection.findSubTypesOf(Model.class);

        for (Class<? extends Model> model : models) {
            Model.validate(model);

            try {
                Model.createTable(model);
            } catch (SQLException | InvalidColumnException e) {
                e.printStackTrace();
            }
        }
    }

    @Nonnull
    private static Logger initLogger() {
        return LoggerFactory.getLogger(Database.class);
    }

}
