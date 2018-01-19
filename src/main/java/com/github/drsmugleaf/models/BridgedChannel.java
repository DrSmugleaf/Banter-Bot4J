package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.translator.Languages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 19/01/2018.
 */
public class BridgedChannel {

    private static Connection connection;
    private long channelID;
    public Languages channelLanguage;
    private long bridgedID;
    public Languages bridgedLanguage;

    public BridgedChannel(long channelID, long bridgedID) {
        this.channelID = channelID;
        this.bridgedID = bridgedID;
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS bridged_channels (" +
                    "channel_id BIGINT REFERENCES guild_channels (channel_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "channel_language VARCHAR(10) NOT NULL," +
                    "bridged_id BIGINT REFERENCES guild_channels (channel_id) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "bridged_language VARCHAR(10) NOT NULL," +
                    "CONSTRAINT channel_bridged_pkey PRIMARY KEY (channel_id, bridged_id)" +
                    ")"
            );
            statement.executeUpdate();
            BridgedChannel.connection = connection;
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Unable to create table bridged_channels", e);
            System.exit(1);
        }
    }

    public static List<BridgedChannel> get(long channelID) {
        List<BridgedChannel> bridgedChannels = new ArrayList<>();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM bridged_channels " +
                    "WHERE bridged_channels.channel_id = ?"
            );
            statement.setLong(1, channelID);

            ResultSet result = statement.executeQuery();
            BridgedChannel channel;
            while (result.next()) {
                channelID = result.getLong("channel_id");
                Languages channelLanguage = Languages.getLanguage(result.getString("channel_language"));
                long bridgedID = result.getLong("bridged_id");
                Languages bridgedLanguage = Languages.getLanguage(result.getString("bridged_language"));
                channel = new BridgedChannel(channelID, bridgedID);
                channel.channelLanguage = channelLanguage;
                channel.bridgedLanguage = bridgedLanguage;
                bridgedChannels.add(channel);
            }

            return bridgedChannels;
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error retrieving bridged channels with channel id " + channelID, e);
            return bridgedChannels;
        }
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO bridged_channels (channel_id, channel_language, bridged_id, bridged_language) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (channel_id, bridged_id) DO UPDATE " +
                    "SET " +
                    "channel_id = ?, " +
                    "channel_language = ?, " +
                    "bridged_id = ?, " +
                    "bridged_language = ? " +
                    "WHERE bridged_channels.channel_id = ? AND bridged_channels.bridged_id = ?"
            );
            statement.setLong(1, channelID);
            statement.setString(2, channelLanguage.getCode());
            statement.setLong(3, bridgedID);
            statement.setString(4, bridgedLanguage.getCode());
            statement.setLong(5, channelID);
            statement.setString(6, channelLanguage.getCode());
            statement.setLong(7, bridgedID);
            statement.setString(8, bridgedLanguage.getCode());
            statement.setLong(9, channelID);
            statement.setLong(10, bridgedID);
            statement.executeUpdate();
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error(
                    "Error saving bridged channel with channel id " + channelID +
                    " and channel language " + channelLanguage.getCode() +
                    " and bridged id " + bridgedID +
                    " and bridged language " + bridgedLanguage.getCode(), e
            );
        }
    }

}
