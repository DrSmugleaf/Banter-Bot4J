package com.github.drsmugleaf.commands.image;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.ConverterRegistry;
import com.github.drsmugleaf.commands.api.tags.Tags;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 10/11/2018
 */
@CommandInfo(
        tags = {Tags.DELETE_COMMAND_MESSAGE},
        description = "Merge any amount of images into one single image"
)
public class MergeImages extends Command {

    @Argument(position = 1, examples = "horizontal")
    private Orientation orientation;

    @Override
    public void run() {
        if (ARGUMENTS.size() < 2) {
            reply("You didn't give any image links.").subscribe();
            return;
        } else if (ARGUMENTS.size() < 3) {
            reply("You only gave one image link.").subscribe();
            return;
        }

        try {
            ARGUMENTS.remove(0);
            BufferedImage image = orientation.concatenate(ARGUMENTS);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            reply(message -> message.addFile("image.png", is)).subscribe();
        } catch (InvalidURLException e) {
            if (e.getCause().getCause().getMessage().contains("403 for URL")) {
                reply("I don't have permission to access one of the links. Please upload this one to imgur: " + e.URL).subscribe();
                return;
            }

            reply("Invalid URL: " + e.URL).subscribe();
        } catch (IOException e) {
            reply("An error occurred merging the images, try again later or contact the bot owner.").subscribe();
            BanterBot4J.warn("Error writing image", e);
        }
    }

    @Override
    public void registerConverters(ConverterRegistry converter) {
        converter.registerCommandTo(Orientation.class, (s, e) -> Orientation.valueOf(s.toUpperCase()));
    }

}
