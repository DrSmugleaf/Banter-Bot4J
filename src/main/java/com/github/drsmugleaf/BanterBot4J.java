package com.github.drsmugleaf;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.Handler;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.reflection.Reflection;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class BanterBot4J {

    @Nonnull
    public static final Logger LOGGER = initLogger();

    @Nonnull
    public static final IDiscordClient CLIENT = buildClient();

    @Nonnull
    public static final String BOT_PREFIX = Keys.BOT_PREFIX.VALUE;

    @Nonnull
    public static final ImmutableList<Long> OWNERS = ImmutableList.copyOf(
            Arrays.asList(
                    109067752286715904L
            )
    );

    @Nullable
    private static IChannel DISCORD_WARNING_CHANNEL = null;

    @Nonnull
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    @Nonnull
    private static IDiscordClient buildClient() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(Keys.DISCORD_TOKEN.VALUE).withRecommendedShardCount();
        return clientBuilder.build();
    }

    @Nonnull
    private static Logger initLogger() {
        return LoggerFactory.getLogger(BanterBot4J.class);
    }

    private static void registerListeners() {
        Reflection reflection = new Reflection("com.github.drsmugleaf");
        List<Class<?>> listenerClasses = reflection.findClassesWithMethodAnnotation(EventSubscriber.class);
        listenerClasses.forEach(clazz -> CLIENT.getDispatcher().registerListener(clazz));
    }

    public static void main(String[] args) {
        Database.init("com.github.drsmugleaf.database.models");
        registerListeners();
        Handler handler = new Handler("com.github.drsmugleaf.commands");
        CLIENT.getDispatcher().registerListener(handler);

        CLIENT.login();
    }

    private static void warnChannel(@Nonnull String message, @Nullable Throwable t) {
        if (DISCORD_WARNING_CHANNEL == null) {
            throw new IllegalStateException("No Discord warning channel has been set");
        }

        StringBuilder warning = new StringBuilder();

        String date = DATE_FORMAT.format(Instant.now());
        warning
                .append("**Warning on ")
                .append(date)
                .append("**\n")
                .append("**Message:** ")
                .append(message);
        if (t != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            t.printStackTrace(printWriter);
            String stackTrace = stringWriter.toString();

            warning
                    .append("\n")
                    .append("**Error:** ")
                    .append(stackTrace);
        }

        Command.sendMessage(DISCORD_WARNING_CHANNEL, warning.toString());
    }

    public static void warn(@Nonnull String message, @Nullable Throwable t) {
        if (t != null) {
            LOGGER.warn(message, t);
        } else {
            LOGGER.warn(message);
        }

        if (DISCORD_WARNING_CHANNEL != null) {
            warnChannel(message, t);
        }
    }

    public static void warn(@Nonnull String message) {
        warn(message, null);
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        String channelIDString = Keys.DISCORD_WARNING_CHANNEL.VALUE;
        if (channelIDString.isEmpty()) {
            LOGGER.warn("No discord warning channel id has been set as an environment property");
            return;
        }

        Long channelID;
        try {
            channelID = Long.parseLong(channelIDString);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(channelIDString + " isn't a correct discord channel id");
        }

        IChannel channel = CLIENT.getChannelByID(channelID);
        if (channel == null) {
            throw new IllegalStateException("No discord channel exists with id " + channelID);
        }

        DISCORD_WARNING_CHANNEL = channel;
    }

}
