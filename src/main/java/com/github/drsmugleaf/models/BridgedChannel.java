package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;

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
    private long bridgedID;

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
                    "bridged_id BIGINT REFERENCES guild_channels (channel_id) ON UPDATE CASCADE ON DELETE CASCADE," +
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

    public void createIfNotExists() {
        Channel channel = new Channel(channelID);
        channel.createIfNotExists();
        Channel bridged = new Channel(bridgedID);
        bridged.createIfNotExists();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO bridged_channels (channel_id, bridged_id) " +
                    "VALUES(?, ?) " +
                    "ON CONFLICT DO NOTHING"
            );
            statement.setLong(1, channelID);
            statement.setLong(2, bridgedID);
            statement.executeUpdate();
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error creating guild channel with channel id " + channelID + " and bridged id " + bridgedID, e);
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
                long bridgedID = result.getLong("bridged_id");
                channel = new BridgedChannel(channelID, bridgedID);
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
                    "INSERT INTO bridged_channels (channel_id, bridged_id) " +
                    "VALUES (?, ?) " +
                    "ON CONFLICT (channel_id, bridged_id) DO UPDATE " +
                    "SET " +
                    "channel_id = ?, " +
                    "bridged_id = ? " +
                    "WHERE bridged_channels.channel_id = ? AND bridged_channels.bridged_id = ?"
            );
            statement.setLong(1, channelID);
            statement.setLong(2, bridgedID);
            statement.setLong(3, channelID);
            statement.setLong(4, bridgedID);
            statement.setLong(5, channelID);
            statement.setLong(6, bridgedID);
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error saving bridged channel with channel id " + channelID + " and bridged id " + bridgedID, e);
        }
    }

}
