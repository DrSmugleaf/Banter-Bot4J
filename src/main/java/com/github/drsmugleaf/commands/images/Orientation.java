package com.github.drsmugleaf.commands.images;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 17/06/2019
 */
public enum Orientation {

    HORIZONTAL {
        @Nonnull
        @Override
        public BufferedImage concatenate(@Nonnull List<String> urls) throws InvalidURLException {
            List<BufferedImage> images = toImages(urls);
            int height = getMaxHeight(images);
            int width = getTotalWidth(images);
            int currentWidth = 0;
            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = finalImage.createGraphics();

            for (BufferedImage image : images) {
                graphics.drawImage(image, currentWidth, 0, null);
                currentWidth += image.getWidth();
            }

            graphics.dispose();

            return finalImage;
        }
    },
    VERTICAL {
        @Nonnull
        @Override
        public BufferedImage concatenate(@Nonnull List<String> urls) throws InvalidURLException {
            List<BufferedImage> images = toImages(urls);
            int height = getTotalHeight(images);
            int width = getMaxWidth(images);
            int currentHeight = 0;
            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = finalImage.createGraphics();

            for (BufferedImage image : images) {
                graphics.drawImage(image, 0, currentHeight, null);
                currentHeight += image.getHeight();
            }

            graphics.dispose();

            return finalImage;
        }
    };

    @Nonnull
    private static BufferedImage toImage(@Nonnull String urlString) throws InvalidURLException {
        URL url;

        try {
            url = new URL(urlString);
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new InvalidURLException(urlString, e);
        }
    }

    @Nonnull
    private static List<BufferedImage> toImages(@Nonnull List<String> urls) throws InvalidURLException {
        return urls.stream().map(Orientation::toImage).collect(Collectors.toList());
    }

    private static int getMaxHeight(@Nonnull List<BufferedImage> images) {
        return Collections.max(images, Comparator.comparingInt(BufferedImage::getHeight)).getHeight();
    }

    private static int getTotalHeight(@Nonnull List<BufferedImage> images) {
        return images.stream().mapToInt(BufferedImage::getHeight).sum();
    }

    private static int getMaxWidth(@Nonnull List<BufferedImage> images) {
        return Collections.max(images, Comparator.comparingInt(BufferedImage::getWidth)).getWidth();
    }

    private static int getTotalWidth(@Nonnull List<BufferedImage> images) {
        return images.stream().mapToInt(BufferedImage::getWidth).sum();
    }

    @Nonnull
    public abstract BufferedImage concatenate(@Nonnull List<String> urls) throws InvalidURLException;

}
