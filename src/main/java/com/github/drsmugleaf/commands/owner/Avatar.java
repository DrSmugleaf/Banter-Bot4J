package com.github.drsmugleaf.commands.owner;

import com.github.drsmugleaf.commands.api.arguments.Argument;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        tags = {Tags.OWNER_ONLY},
        description = "Change the bot's image"
)
public class Avatar extends Command {

    @Argument(position = 1, examples = "<https://cdn.discordapp.com/avatars/403896467766378496/106cdfc7de7e0ee5b4cac0b187218801.png>")
    private String link;

    @Override
    public void run() {
        try {
            URL url = new URL(link);
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

            while (suffix == null && readers.hasNext()) {
                ImageReaderSpi provider = readers.next().getOriginatingProvider();
                if (provider != null) {
                    String[] suffixes = provider.getFileSuffixes();
                    if (suffixes != null) {
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
        } catch (MalformedURLException e) {
            reply("Malformed url: <" + link + ">");
        } catch (IOException e) {
            LOGGER.error("Error opening connection", e);
            reply("Invalid image URL").subscribe();
        }
    }

}
