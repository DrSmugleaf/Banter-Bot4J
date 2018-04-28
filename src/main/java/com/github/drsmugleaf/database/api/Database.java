package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
public class Database {

    @Nonnull
    static final Logger LOGGER = initLogger();

    @Nonnull
    static final Connection CONNECTION = DatabaseConnection.initialize();

    @Nonnull
    private static Logger initLogger() {
        return LoggerFactory.getLogger(Database.class);
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    private static <T extends Model<T>> List<Class<T>> getModels(@Nonnull String packageName) {
        List<Class<T>> models = new ArrayList<>();
        Reflection reflection = new Reflection(packageName);

        try {
            for (Class<?> model : reflection.getClasses()) {
                if (!Model.class.isAssignableFrom(model)) {
                    continue;
                }

                models.add((Class<T>) model);
            }
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            LOGGER.error("Error getting classes from package " + packageName);
        }

        return models;
    }

    public static <T extends Model<T>> void init(@Nonnull String packageName) {
        List<Class<T>> models = getModels(packageName);

        for (Class<T> model : models) {
            ModelValidator.validateAll(model);
            Model.createTable(model);
        }
    }

}
