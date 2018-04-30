package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
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
public class Owner extends AbstractCommand {

    @CommandInfo(tags = {Tags.OWNER_ONLY})
    public static void avatar(MessageReceivedEvent event, List<String> args) {
        if (args.isEmpty()) {
            sendMessage(event.getChannel(), "You didn't provide a link to change the bot's image to.");
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
            BanterBot4J.LOGGER.error("Malformed URL or error opening connection", e);
            sendMessage(event.getChannel(), "Invalid image URL");
        }
    }

    @CommandInfo(tags = {Tags.OWNER_ONLY})
    public static void name(MessageReceivedEvent event, List<String> args) {
        String name = String.join(" ", args);
        event.getClient().changeUsername(String.join(" ", args));
        sendMessage(event.getChannel(), "Changed the bot's name to " + name);
    }

    @CommandInfo(tags = {Tags.OWNER_ONLY})
    public static void playing(MessageReceivedEvent event, List<String> args) {
        if(args.isEmpty()) {
            event.getClient().changePlayingText(null);
            sendMessage(event.getChannel(), "Reset the bot's playing status");
            return;
        }

        String game = String.join(" ", args);
        event.getClient().changePlayingText(game);
        sendMessage(event.getChannel(), "Changed the bot's playing status to " + game);
    }
}
