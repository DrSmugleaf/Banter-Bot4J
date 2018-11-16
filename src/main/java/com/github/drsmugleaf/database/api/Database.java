package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.reflection.Reflection;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jetbrains.annotations.NotNull;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
public class Database {

    @NotNull
    static final Logger LOGGER = initLogger();

    @NotNull
    private static final HikariDataSource DATA_SOURCE = DatabaseConnection.initialize();

    @NotNull
    private static Logger initLogger() {
        return LoggerFactory.getLogger(Database.class);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private static <T extends Model<T>> List<Class<T>> getModels(@NotNull String packageName) {
        List<Class<T>> models = new ArrayList<>();
        Reflection reflection = new Reflection(packageName);
        for (Class<?> model : reflection.getClasses()) {
            if (!Model.class.isAssignableFrom(model)) {
                continue;
            }

            models.add((Class<T>) model);
        }

        return models;
    }

    public static <T extends Model<T>> void init(@NotNull String packageName) {
        List<Class<T>> models = getModels(packageName);

        for (Class<T> model : models) {
            ModelValidator.validateAll(model);
            Model.createTable(model);
        }
    }

    @NotNull
    public static Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Error getting connection from data source", e);
        }
    }

}
