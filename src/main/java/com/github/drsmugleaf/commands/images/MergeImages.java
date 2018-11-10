package com.github.drsmugleaf.commands.images;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/11/2018
 */
@CommandInfo(tags = {Tags.DELETE_COMMAND_MESSAGE})
public class MergeImages extends Command {

    protected MergeImages(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static BufferedImage concatenateImagesVertical(@Nonnull List<String> urls) throws InvalidURLException {
        int amount = urls.size();
        BufferedImage images[] = new BufferedImage[amount];
        int maxWidth = 0;
        int totalHeight = 0;
        for (int i = 0; i < urls.size(); i++) {
            String urlName = urls.get(i);
            URL url;
            BufferedImage urlImage;
            try {
                url = new URL(urlName);
                urlImage = ImageIO.read(url);
            } catch (IOException e) {
                throw new InvalidURLException(urlName);
            }

            images[i] = urlImage;
            maxWidth = images[i].getWidth() > maxWidth ? urlImage.getWidth() : maxWidth;
            totalHeight += images[i].getHeight();
        }

        int currentHeight = 0;
        BufferedImage image = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        for (BufferedImage bufferedImage : images) {
            graphics.drawImage(bufferedImage, 0, currentHeight, null);
            currentHeight += bufferedImage.getHeight();
        }

        graphics.dispose();

        return image;
    }

    @Nonnull
    private static BufferedImage concatenateImagesHorizontal(@Nonnull List<String> urls) throws InvalidURLException {
        int amount = urls.size();
        BufferedImage images[] = new BufferedImage[amount];
        int maxHeight = 0;
        int totalWidth = 0;
        for (int i = 0; i < urls.size(); i++) {
            String urlName = urls.get(i);
            URL url;
            BufferedImage urlImage;
            try {
                url = new URL(urlName);
                urlImage = ImageIO.read(url);
            } catch (IOException e) {
                throw new InvalidURLException(urlName);
            }

            images[i] = urlImage;
            maxHeight = images[i].getHeight() > maxHeight ? urlImage.getHeight() : maxHeight;
            totalWidth += images[i].getWidth();
        }

        int currentWidth = 0;
        BufferedImage image = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        for (BufferedImage bufferedImage : images) {
            graphics.drawImage(bufferedImage, currentWidth, 0, null);
            currentWidth += bufferedImage.getWidth();
        }

        graphics.dispose();

        return image;
    }

    @Override
    public void run() {
        String orientation = ARGS.first().toLowerCase();
        if (!orientation.equals("vertical") && !orientation.equals("horizontal")) {
            EVENT.reply("Invalid orientation. Valid orientations: vertical, horizontal.");
            return;
        }

        ARGS.remove(0);
        if (ARGS.size() < 2) {
            EVENT.reply("You only gave 1 image link.");
            return;
        }

        try {
            BufferedImage image;
            if (orientation.equals("vertical")) {
                image = concatenateImagesVertical(ARGS);
            } else {
                image = concatenateImagesHorizontal(ARGS);
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            EVENT.getChannel().sendFile("", is, "image.png");
        } catch (InvalidURLException e) {
            EVENT.reply("Invalid URL: " + e.URL);
        } catch (IOException e) {
            EVENT.reply("An error occurred making the new image, try again later or contact the bot owner.");
            BanterBot4J.warn("Error writing image", e);
        }
    }

}
