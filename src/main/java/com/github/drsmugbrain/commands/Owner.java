package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.Image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DrSmugleaf on 20/05/2017.
 */
public class Owner {

    @Command
    public static void avatar(MessageReceivedEvent event, List<String> args) {
        if(!Bot.isOwner(event.getAuthor().getLongID())) {
            Bot.sendMessage(event.getChannel(), "You don't have permission to change the bot's image");
            return;
        }

        try {
            URL url = new URL(args.get(0));
            URLConnection connection = url.openConnection();
            String contentType = connection.getContentType();

            String suffix = null;
            Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType(contentType);

            while(suffix == null && readers.hasNext()) {
                ImageReaderSpi provider = readers.next().getOriginatingProvider();
                if(provider != null) {
                    String[] suffixes = provider.getFileSuffixes();
                    if(suffixes != null) {
                        suffix = suffixes[0];
                    }
                }
            }

            event.getClient().changeAvatar(Image.forUrl(suffix, args.get(0)));
        } catch(IOException e) {
            Bot.LOGGER.error("Malformed URL or error opening connection", e);
            Bot.sendMessage(event.getChannel(), "Invalid image URL");
        }
    }

    @Command
    public static void name(MessageReceivedEvent event, List<String> args) {
        if(!Bot.isOwner(event.getAuthor().getLongID())) {
            Bot.sendMessage(event.getChannel(), "You don't have permission to change the bot's name");
            return;
        }

        String name = String.join(" ", args);
        event.getClient().changeUsername(String.join(" ", args));
        Bot.sendMessage(event.getChannel(), "Changed the bot's name to " + name);
    }

    @Command
    public static void playing(MessageReceivedEvent event, List<String> args) {
        if(!Bot.isOwner(event.getAuthor().getLongID())) {
            Bot.sendMessage(event.getChannel(), "You don't have permission to change the bot's playing status");
            return;
        }

        if(args.isEmpty()) {
            event.getClient().changePlayingText(null);
            Bot.sendMessage(event.getChannel(), "Reset the bot's playing status");
            return;
        }

        String game = String.join(" ", args);
        event.getClient().changePlayingText(game);
        Bot.sendMessage(event.getChannel(), "Changed the bot's playing status to " + game);
    }
}