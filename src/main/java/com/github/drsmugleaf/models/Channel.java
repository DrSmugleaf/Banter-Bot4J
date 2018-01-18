package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class Channel {

    private static Connection connection;
    private long id;

    public Channel(long id) {
        this.id = id;
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS channels(" +
                    "id BIGINT PRIMARY KEY" +
                    ")");
            statement.executeUpdate();
            Channel.connection = connection;
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Unable to database table channels", e);
            System.exit(1);
        }
    }

    public void createIfNotExists() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO channels (id) " +
                    "VALUES (?)" +
                    "ON CONFLICT DO NOTHING"
            );
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error creating channel with channel id " + id, e);
        }
    }

    public static Channel get(long id) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT 1 FROM channels" +
                    "WHERE id = ?"
            );
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            id = result.getLong("id");
            return new Channel(id);
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error retrieving channel with id " + id, e);
            return null;
        }
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO channels (id) " +
                    "VALUES(?) " +
                    "ON CONFLICT (id) DO UPDATE" +
                    "SET " +
                    "id = ?, " +
                    "WHERE id = ?"
            );
            statement.setLong(1, id);
            statement.setLong(2, id);
            statement.setLong(3, id);
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error saving channel with id " + id, e);
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        for (IChannel iChannel : event.getClient().getChannels()) {
            Channel channel = new Channel(iChannel.getLongID());
            channel.createIfNotExists();
        }
    }

}
