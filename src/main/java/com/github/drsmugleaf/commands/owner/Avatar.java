package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.util.Image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            reply("You didn't provide a link to change the bot's image to.").subscribe();
            return;
        }

        try {
            URL url = new URL(ARGUMENTS.get(0));
            URLConnection connection = url.openConnection();
            String contentType = connection.getContentType();

            byte[] data = new byte[]{};
            try (InputStream stream = connection.getInputStream()) {
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                int n;

                while ((n = stream.read(data)) > 0) {
                    byteOutput.write(data, 0, n);
                }
            }

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

            if (suffix == null) {
                reply("Unable to get the image's extension").subscribe();
                return;
            }

            Image image = Image.ofRaw(data, Image.Format.valueOf(suffix));
            EVENT.getClient().edit(edit -> edit.setAvatar(image));
        } catch(IOException e) {
            LOGGER.error("Malformed URL or error opening connection", e);
            reply("Invalid image URL").subscribe();
        }
    }

}
