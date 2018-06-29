package com.github.drsmugleaf;

import com.github.drsmugleaf.blackjack.BlackjackEventHandler;
import com.github.drsmugleaf.blackjack.EventDispatcher;
import com.github.drsmugleaf.commands.api.Handler;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.reflection.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;

import javax.annotation.Nonnull;
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
        List<Class<?>> listenerClasses = reflection.findClassesWithMethodAnnotation(EventSubscriber.class);
        listenerClasses.forEach(clazz -> CLIENT.getDispatcher().registerListener(clazz));

        List<Class<?>> blackjackListeners = reflection.findClassesWithMethodAnnotation(BlackjackEventHandler.class);
        EventDispatcher.registerListeners(blackjackListeners);
    }

    public static void main(String[] args) {
        Database.init("com.github.drsmugleaf.database.models");
        registerListeners();
        Handler.setOwners(OWNERS);
        Handler.setBotPrefix(Keys.BOT_PREFIX.VALUE);
        Handler handler = new Handler("com.github.drsmugleaf.commands");
        CLIENT.getDispatcher().registerListener(handler);

        CLIENT.login();
    }

}
