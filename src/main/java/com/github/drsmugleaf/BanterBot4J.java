package com.github.drsmugleaf;

import com.github.drsmugleaf.commands.Handler;
import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.models.Database;
import com.github.drsmugleaf.models.Guild;
import com.github.drsmugleaf.models.Member;
import com.github.drsmugleaf.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.util.Arrays;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class BanterBot4J {

    public static IDiscordClient client = null;
    public static final String BOT_PREFIX = Env.get(Keys.BOT_PREFIX);
    public static final Long[] OWNERS = {109067752286715904L};
    public static final Logger LOGGER = initLogger();

    private static Logger initLogger() {
        return LoggerFactory.getLogger(BanterBot4J.class);
    }

    public static void main(String[] args){
        IDiscordClient cli = buildClient(Env.get(Keys.DISCORD_TOKEN));

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new Handler());
        cli.getDispatcher().registerListeners(Guild.class, User.class, Member.class);
        new Database();

        User.createTable(Database.conn);
        Guild.createTable(Database.conn);
        Member.createTable(Database.conn);
        // Only login after all events are registered otherwise some may be missed.
        cli.login();
    }

    public static IDiscordClient buildClient(String token){
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder
                .withToken(token)
                .withRecommendedShardCount();
        client = clientBuilder.build();
        return client;
    }

    public static boolean isOwner(Long userID) {
        return Arrays.stream(BanterBot4J.OWNERS).anyMatch(id -> id.equals(userID));
    }

}
