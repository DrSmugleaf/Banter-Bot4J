package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import sx.blah.discord.util.Image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(tags = {Tags.OWNER_ONLY})
public class Avatar extends Command {

    @Override
    public void run() {
        if (ARGUMENTS.isEmpty()) {
            EVENT.reply("You didn't provide a link to change the bot's image to.");
            return;
        }

        try {
            URL url = new URL(ARGUMENTS.get(0));
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

            EVENT.getClient().changeAvatar(Image.forUrl(suffix, ARGUMENTS.get(0)));
        } catch(IOException e) {
            LOGGER.error("Malformed URL or error opening connection", e);
            EVENT.reply("Invalid image URL");
        }
    }

}
