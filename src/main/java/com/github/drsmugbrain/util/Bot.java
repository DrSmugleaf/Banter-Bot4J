package com.github.drsmugbrain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.Arrays;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class Bot {

    // Constants for use throughout the bot
    public static final String BOT_PREFIX = Env.readFile().get("BOT_PREFIX");
    public static final Long[] OWNERS = {159650451447480320L, 109067752286715904L};
    public static final Logger LOGGER = initLogger();

    private static Logger initLogger() {
        return LoggerFactory.getLogger(Bot.class);
    }

    // Handles the creation and getting of a IDiscordClient object for a token
    public static IDiscordClient buildClient(String token){
        // The ClientBuilder object is where you will attach your params for configuring the instance of your bot.
        // Such as withToken, setDaemon etc
        return new ClientBuilder().withToken(token).withRecommendedShardCount().build();
    }

    // Helper functions to make certain aspects of the bot easier to use.
    public static void sendMessage(IChannel channel, String message){
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                Bot.LOGGER.error("Message could not be sent", e);
            }
        });
    }

    public static boolean isOwner(Long userID) {
        return Arrays.stream(Bot.OWNERS).anyMatch(id -> id.equals(userID));
    }

}
