package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.util.Reflection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
public class Database {

    public static final Connection CONNECTION = DatabaseConnection.initialize();

    static {
        Reflection reflection = new Reflection("com.github.drsmugleaf.database.models");
        List<Class<? extends Model>> models = reflection.findSubTypesOf(Model.class);

        for (Class<? extends Model> model : models) {
            Table tableAnnotation = model.getDeclaredAnnotation(Table.class);

            try {
                Model.createTable(model);
            } catch (SQLException | InvalidColumnException e) {
                e.printStackTrace();
            }
        }
    }

}
