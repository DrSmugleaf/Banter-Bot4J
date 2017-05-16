package com.github.drsmugbrain.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 16/05/2017.
 */
public class Blacklist {

    private static Connection connection;
    private int guildID;
    private int userID;

    public Blacklist(int guildID, int userID) {
        this.guildID = guildID;
        this.userID = userID;
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS blacklist (" +
                    "guild_id BIGINT," +
                    "user_id BIGINT" +
                    ")");
            statement.executeUpdate();
            Blacklist.connection = connection;
        } catch(SQLException e) {
            System.err.println("Unable to create table blacklist");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO blacklist (guild_id, user_id) VALUES(?, ?)");
            statement.setInt(1, this.guildID);
            statement.setInt(2, this.userID);
        } catch(SQLException e) {
            System.err.println("Error saving blacklist with guild id " + this.guildID + " and user id " + this.userID);
            e.printStackTrace();
        }
    }

}
