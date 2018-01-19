package com.github.drsmugleaf;

import com.github.drsmugleaf.commands.Handler;
import com.github.drsmugleaf.commands.Translator;
import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class BanterBot4J {

    @Nonnull
    private static final IDiscordClient CLIENT = buildClient();

    @Nonnull
    public static final String BOT_PREFIX = getBotPrefix();

    @Nonnull
    private static final Long[] OWNERS = {109067752286715904L};

    @Nonnull
    public static final Logger LOGGER = initLogger();

    private static IDiscordClient buildClient() {
        ClientBuilder clientBuilder = new ClientBuilder();
        String token = Env.get(Keys.DISCORD_TOKEN);
        clientBuilder.withToken(token).withRecommendedShardCount();
        return clientBuilder.build();
    }

    private static String getBotPrefix() {
        String envPrefix = Env.get(Keys.BOT_PREFIX);
        return envPrefix == null ? "!" : envPrefix;
    }

    private static Logger initLogger() {
        return LoggerFactory.getLogger(BanterBot4J.class);
    }

    public static void main(String[] args) {
        CLIENT.getDispatcher().registerListener(new Handler());
        CLIENT.getDispatcher().registerListeners(Guild.class, User.class, Member.class, Channel.class, GuildChannel.class, BridgedChannel.class, Translator.class);
        new Database();

        User.createTable(Database.conn);
        Guild.createTable(Database.conn);
        Member.createTable(Database.conn);
        Channel.createTable(Database.conn);
        GuildChannel.createTable(Database.conn);
        BridgedChannel.createTable(Database.conn);

        CLIENT.login();
    }

    public static boolean isOwner(Long userID) {
        return Arrays.stream(BanterBot4J.OWNERS).anyMatch(id -> id.equals(userID));
    }

}
