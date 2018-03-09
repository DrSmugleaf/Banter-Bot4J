package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 16/05/2017.
 */
public class Guild {

    private static Connection connection;
    private long id;

    public Guild(long id) {
        this.id = id;
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS guilds (" +
                    "id BIGINT PRIMARY KEY" +
                    ")");
            statement.executeUpdate();
            Guild.connection = connection;
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Unable to create guilds database table", e);
            System.exit(1);
        }
    }

    public void createIfNotExists() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO guilds (id) " +
                    "VALUES(?) " +
                    "ON CONFLICT DO NOTHING"
            );
            statement.setLong(1, this.id);
            statement.executeUpdate();
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Error creating guild with id " + this.id, e);
        }
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO guilds (id) VALUES (?) ON CONFLICT DO NOTHING");
            statement.setLong(1, this.id);
            statement.executeUpdate();
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error saving guild with id " + this.id, e);
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            event.getClient().getGuilds().forEach(guild -> {
                Long guildID = guild.getLongID();
                Guild guildModel = new Guild(guildID);
                guildModel.createIfNotExists();
            });
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
