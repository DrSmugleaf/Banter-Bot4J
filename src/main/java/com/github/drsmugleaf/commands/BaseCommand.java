package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.util.Bot;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/01/2018.
 */
abstract class BaseCommand {

    protected static void sendMessage(@Nonnull IChannel channel, @Nonnull String message) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                Bot.LOGGER.error("Message could not be sent", e);
                throw e;
            }
        });
    }

    protected static void sendMessage(@Nonnull IChannel channel, EmbedObject embed) {
        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(embed);
            } catch (DiscordException e) {
                Bot.LOGGER.error("Embed could not be sent", e);
                throw e;
            }
        });
    }

}
