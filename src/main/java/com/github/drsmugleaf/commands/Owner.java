package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.Image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * Created by DrSmugleaf on 20/05/2017.
 */
public class Owner {

    @CommandInfo(tags = {Tags.OWNER_ONLY})
    public static void avatar(CommandReceivedEvent event) {
        if (event.ARGS.isEmpty()) {
            event.reply("You didn't provide a link to change the bot's image to.");
            return;
        }

        try {
            URL url = new URL(event.ARGS.get(0));
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

            event.getClient().changeAvatar(Image.forUrl(suffix, event.ARGS.get(0)));
        } catch(IOException e) {
            BanterBot4J.LOGGER.error("Malformed URL or error opening connection", e);
            event.reply("Invalid image URL");
        }
    }

    @CommandInfo(tags = {Tags.OWNER_ONLY})
    public static void name(CommandReceivedEvent event) {
        String name = String.join(" ", event.ARGS);
        event.getClient().changeUsername(String.join(" ", event.ARGS));
        event.reply("Changed the bot's name to " + name);
    }

    @CommandInfo(tags = {Tags.OWNER_ONLY})
    public static void playing(CommandReceivedEvent event) {
        if(event.ARGS.isEmpty()) {
            event.getClient().changePresence(StatusType.ONLINE, null, "");
            event.reply("Reset the bot's playing status");
            return;
        }

        String game = String.join(" ", event.ARGS);
        event.getClient().changePresence(StatusType.ONLINE, ActivityType.PLAYING, game);
        event.reply("Changed the bot's playing status to " + game);
    }
}
