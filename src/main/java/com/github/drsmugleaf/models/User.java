package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
public class User {

    private static Connection connection;
    private long id;

    public User(long id) {
        this.id = id;
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY" +
                    ")");
            statement.executeUpdate();
            User.connection = connection;
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Unable to create users database table", e);
            System.exit(1);
        }
    }

    public void createIfNotExists() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO users (id) " +
                    "VALUES(?) " +
                    "ON CONFLICT DO NOTHING"
            );
            statement.setLong(1, this.id);
            statement.executeUpdate();
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Error creating user with id " + this.id, e);
        }
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO users (id) VALUES (?) ON CONFLICT DO NOTHING");
            statement.setLong(1, this.id);
            statement.executeUpdate();
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Error saving user with id" + this.id, e);
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            event.getClient().getUsers().forEach(user -> {
                Long userID = user.getLongID();
                User userModel = new User(userID);
                userModel.createIfNotExists();
            });
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
