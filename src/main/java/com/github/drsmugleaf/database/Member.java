package com.github.drsmugleaf.database;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 17/05/2017.
 */
public class Member {

    private static Connection connection;
    private long userID;
    private long guildID;
    public boolean isBlacklisted;

    public Member(long userID, long guildID, boolean isBlacklisted) {
        this.userID = userID;
        this.guildID = guildID;
        this.isBlacklisted = isBlacklisted;
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS members (" +
                    "user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "guild_id BIGINT REFERENCES guilds (id) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "is_blacklisted BOOLEAN DEFAULT FALSE," +
                    "CONSTRAINT user_guild_pkey PRIMARY KEY (user_id, guild_id)" +
                    ")");
            statement.executeUpdate();
            Member.connection = connection;
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Unable to create table members", e);
            System.exit(1);
        }
    }

    public void createIfNotExists() {
        User user = new User(userID);
        user.createIfNotExists();
        Guild guild = new Guild(guildID);
        guild.createIfNotExists();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO members (user_id, guild_id, is_blacklisted) " +
                    "VALUES(?, ?, ?) " +
                    "ON CONFLICT DO NOTHING"
            );
            statement.setLong(1, this.userID);
            statement.setLong(2, this.guildID);
            statement.setBoolean(3, this.isBlacklisted);
            statement.executeUpdate();
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Error creating member with user id " + this.userID + " and guild id " + this.guildID + " with blacklist set to " + this.isBlacklisted, e);
        }
    }

    public static Member get(long userID, long guildID) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM members " +
                    "WHERE members.user_id = ? AND members.guild_id = ?"
            );
            statement.setLong(1, userID);
            statement.setLong(2, guildID);
            ResultSet result = statement.executeQuery();
            result.next();
            Long user_id = result.getLong("user_id");
            Long guild_id = result.getLong("guild_id");
            Boolean isBlacklisted = result.getBoolean("is_blacklisted");
            return new Member(user_id, guild_id, isBlacklisted);
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Error retrieving member with user id " + userID + " and guild id " + guildID, e);
        }
        return null;
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO members (user_id, guild_id, is_blacklisted) " +
                    "VALUES(?, ?, ?) " +
                    "ON CONFLICT (user_id, guild_id) DO UPDATE " +
                    "SET " +
                    "user_id = ?, " +
                    "guild_id = ?, " +
                    "is_blacklisted = ? " +
                    "WHERE members.user_id = ? AND members.guild_id = ?"
            );
            statement.setLong(1, this.userID);
            statement.setLong(2, this.guildID);
            statement.setBoolean(3, this.isBlacklisted);
            statement.setLong(4, this.userID);
            statement.setLong(5, this.guildID);
            statement.setBoolean(6, this.isBlacklisted);
            statement.setLong(7, this.userID);
            statement.setLong(8, this.guildID);
            statement.executeUpdate();
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Error saving member with user id " + this.userID + " and guild id " + this.guildID + " with blacklist set to " + this.isBlacklisted, e);
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            event.getClient().getGuilds().forEach(guild -> guild.getUsers().forEach(user -> {
                Long guildID = guild.getLongID();
                Long userID = user.getLongID();
                Member memberModel = new Member(userID, guildID, false);
                memberModel.createIfNotExists();
            }));
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }


}
