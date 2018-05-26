package com.github.drsmugleaf;

import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
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
    private static final Long[] OWNERS = {109067752286715904L};

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
        List<Method> methods = reflection.findMethodsWithAnnotation(EventSubscriber.class);
        methods.forEach(method -> CLIENT.getDispatcher().registerListener(method.getDeclaringClass()));
    }

    public static void main(String[] args) {
        Database.init("com.github.drsmugleaf.database.models");
        registerListeners();

        CLIENT.login();
    }

    public static boolean isOwner(@Nullable Long userID) {
        return Arrays.stream(BanterBot4J.OWNERS).anyMatch(id -> id.equals(userID));
    }

}
