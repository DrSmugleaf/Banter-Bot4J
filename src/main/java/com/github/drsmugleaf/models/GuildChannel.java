package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class GuildChannel {

    private static Connection connection;
    private long channelID;
    private long guildID;
    public List<Long> bridgedTo = new ArrayList<>();

    public GuildChannel(long channelID, long guildID, @Nullable List<Long> bridgedTo) {
        this.channelID = channelID;
        this.guildID = guildID;
        if (bridgedTo != null) {
            this.bridgedTo.addAll(bridgedTo);
        }
    }

    public GuildChannel(long channelID, long guildID) {
        this(channelID, guildID, null);
    }

    public static void createTable(Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS guild_channels (" +
                    "channel_id BIGINT REFERENCES channels (id) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "guild_id BIGINT REFERENCES guilds (id) ON UPDATE CASCADE ON DELETE CASCADE," +
                    "bridged_to BIGINT[] NOT NULL DEFAULT ARRAY[]::bigint[]," +
                    "CONSTRAINT channel_guild_pkey PRIMARY KEY (channel_id, guild_id)" +
                    ")"
            );
            statement.executeUpdate();
            GuildChannel.connection = connection;
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Unable to create table guild_channels", e);
            System.exit(1);
        }
    }

    public void createIfNotExists() {
        Channel channel = new Channel(channelID);
        channel.createIfNotExists();
        Guild guild = new Guild(guildID);
        guild.createIfNotExists();

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO guild_channels (channel_id, guild_id, bridged_to) " +
                    "VALUES(?, ?, ?) " +
                    "ON CONFLICT DO NOTHING"
            );
            statement.setLong(1, channelID);
            statement.setLong(2, guildID);
            Array array = connection.createArrayOf("bigint", bridgedTo.toArray());
            statement.setArray(3, array);
            statement.executeUpdate();
        } catch (SQLException e) {
            String bridgedToString = bridgedTo.stream().map(Object::toString).collect(Collectors.joining(", "));
            BanterBot4J.LOGGER.error("Error creating guild channel with channel id " + channelID + " and guild id " + guildID + " with bridged to set to " + bridgedToString, e);
        }
    }

    public static GuildChannel get(long channelID) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT 1 FROM guild_channels " +
                    "WHERE guild_channels.channel_id = ?"
            );
            ResultSet result = statement.executeQuery();
            result.next();
            channelID = result.getLong("channel_id");
            long guildID = result.getLong("guild_id");
            Long[] array = (Long[]) result.getArray("bridged_to").getArray();
            List<Long> bridgedTo = Arrays.asList(array);
            return new GuildChannel(channelID, guildID, bridgedTo);
        } catch (SQLException e) {
            BanterBot4J.LOGGER.error("Error retrieving guild channel with channel id " + channelID, e);
            return null;
        }
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO guild_channels (channel_id, guild_id, bridged_to) " +
                    "VALUES(?, ?, ?) " +
                    "ON CONFLICT (channel_id, guild_id) DO UPDATE " +
                    "SET " +
                    "channel_id = ?, " +
                    "guild_id = ?, " +
                    "bridged_to = ? " +
                    "WHERE guild_channels.channel_id = ? AND guild_channels.guild_id = ?"
            );
            statement.setLong(1, channelID);
            statement.setLong(2, guildID);
            Array array = connection.createArrayOf("BIGINT", bridgedTo.toArray());
            statement.setArray(3, array);
            statement.setLong(4, channelID);
            statement.setLong(5, guildID);
            statement.setArray(6, array);
            statement.setLong(7, channelID);
            statement.setLong(8, guildID);
        } catch (SQLException e) {
            String bridgedToString = bridgedTo.stream().map(Object::toString).collect(Collectors.joining(", "));
            BanterBot4J.LOGGER.error("Error saving guild channel with channel id " + channelID + " and guild id " + guildID + " with bridged to set to " + bridgedToString, e);
        }
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        for (IGuild iGuild : event.getClient().getGuilds()) {
            for (IChannel iChannel : iGuild.getChannels()) {
                long channelID = iChannel.getLongID();
                long guildID = iGuild.getLongID();
                GuildChannel guildChannel = new GuildChannel(channelID, guildID);
                guildChannel.createIfNotExists();
            }
        }
    }

}
