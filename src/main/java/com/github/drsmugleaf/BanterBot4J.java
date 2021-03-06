package com.github.drsmugleaf;

import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.commands.api.Handler;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.reflection.Reflection;
import com.google.common.collect.ImmutableList;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class BanterBot4J {

    public static final Logger LOGGER = initLogger();
    public static final DiscordClient CLIENT = initClient();
    public static final String BOT_PREFIX = Keys.BOT_PREFIX.VALUE;
    public static final ImmutableList<Long> OWNERS = ImmutableList.of(
            109067752286715904L
    );
    @Nullable
    private static MessageChannel DISCORD_WARNING_CHANNEL = null;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);
    private static final Handler HANDLER = initHandler();

    private static DiscordClient initClient() {
        DiscordClientBuilder builder = new DiscordClientBuilder(Keys.DISCORD_TOKEN.VALUE);
        return builder.build();
    }

    private static Logger initLogger() {
        return LoggerFactory.getLogger(BanterBot4J.class);
    }

    @Contract(" -> new")
    private static Handler initHandler() {
        return new Handler("com.github.drsmugleaf.commands");
    }

    private static void registerListeners() {
        Reflection reflection = new Reflection("com.github.drsmugleaf");
        List<Method> methods = reflection.findMethodsWithAnnotation(EventListener.class);
        for (Method listener : methods) {
            EventListener annotation = listener.getDeclaredAnnotation(EventListener.class);
            CLIENT
                    .getEventDispatcher()
                    .on(annotation.value())
                    .subscribe(event -> {
                        try {
                            listener.invoke(listener.getDeclaringClass(), event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            warn("Error invoking listener " + listener.getDeclaringClass(), e);
                        }
                    });
        }
    }

    public static void main(String[] args) {
        Database.init("com.github.drsmugleaf.database.model");
        registerListeners();
        CLIENT
                .getEventDispatcher()
                .on(MessageCreateEvent.class)
                .flatMap(m -> HANDLER.handle(m).onErrorResume(e -> {
                    warn("Error handling message with id " + m.getMessage().getId().asLong(), e);
                    return Mono.empty();
                }))
                .subscribe();

        CLIENT
                .login()
                .block();
    }

    private static void warnChannel(String message, @Nullable Throwable t) {
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

        DISCORD_WARNING_CHANNEL.createMessage(spec -> spec.setContent(warning.toString()));
    }

    public static void warn(String message, @Nullable Throwable t) {
        if (t != null) {
            LOGGER.warn(message, t);
        } else {
            LOGGER.warn(message);
        }

        if (DISCORD_WARNING_CHANNEL != null) {
            warnChannel(message, t);
        }
    }

    public static void warn(String message) {
        warn(message, null);
    }

    @EventListener(ReadyEvent.class)
    public static void handle(ReadyEvent event) {
        String channelIDString = Keys.DISCORD_WARNING_CHANNEL.VALUE;
        if (channelIDString.isEmpty()) {
            LOGGER.warn("No discord warning channel id has been set as an environment property");
            return;
        }

        Snowflake channelID;
        try {
            channelID = Snowflake.of(Long.parseLong(channelIDString));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(channelIDString + " isn't a correct discord channel id");
        }

        event
                .getClient()
                .getChannelById(channelID)
                .cast(TextChannel.class)
                .subscribe(channel -> {
                    DISCORD_WARNING_CHANNEL = channel;
                    LOGGER.info("Discord warning channel has been set to " + channel.getName());
                });
    }

    @Contract(pure = true)
    public static Handler getHandler() {
        return HANDLER;
    }

}
