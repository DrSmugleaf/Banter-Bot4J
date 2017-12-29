package com.github.drsmugleaf.util;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DrSmugleaf on 19/05/2017.
 */
public class Bot {

    public static IDiscordClient client = null;
    public static final String BOT_PREFIX = Env.readFile().get("BOT_PREFIX");
    public static final Long[] OWNERS = {159650451447480320L, 109067752286715904L};
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

    public static void sendMessage(IChannel channel, String message){
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                Bot.LOGGER.error("Message could not be sent", e);
                throw e;
            }
        });
    }

    public static void sendMessage(IChannel channel, EmbedObject embed) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(embed);
            } catch (DiscordException e) {
                Bot.LOGGER.error("Message could not be sent", e);
            }
        });
    }

    public static IUser fetchUser(long id) {
        final IUser[] user = new IUser[1];

        RequestBuffer.request(() -> {
            try {
                user[0] = Bot.client.fetchUser(id);
            } catch (DiscordException e) {
                Bot.LOGGER.error("User couldn't be fetched", e);
                throw e;
            }
        }).get();

        return user[0];
    }

    public static boolean isOwner(Long userID) {
        return Arrays.stream(Bot.OWNERS).anyMatch(id -> id.equals(userID));
    }

    @Nullable
    public static IRole getHighestRole(List<IRole> roles) {
        if(roles.isEmpty()) return null;
        roles.sort(Comparator.comparingInt(IRole::getPosition));
        return roles.get(roles.size() - 1);
    }

    @Nullable
    public static IRole getHighestRole(IUser user, IGuild guild) {
        List<IRole> roles = user.getRolesForGuild(guild);
        return Bot.getHighestRole(roles);
    }

}
