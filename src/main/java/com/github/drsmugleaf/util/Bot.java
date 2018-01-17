package com.github.drsmugleaf.util;

import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import java.util.Arrays;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class Bot {

    public static IDiscordClient client = null;
    public static final String BOT_PREFIX = Env.get(Keys.BOT_PREFIX);
    public static final Long[] OWNERS = {109067752286715904L};
    public static final Logger LOGGER = initLogger();

    private static Logger initLogger() {
        return LoggerFactory.getLogger(Bot.class);
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
        return Arrays.stream(Bot.OWNERS).anyMatch(id -> id.equals(userID));
    }

}
