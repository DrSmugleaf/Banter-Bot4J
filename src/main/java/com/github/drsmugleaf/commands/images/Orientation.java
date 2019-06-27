package com.github.drsmugleaf.commands.images;

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
        @Override
        public BufferedImage concatenate(List<String> urls) throws InvalidURLException {
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
        @Override
        public BufferedImage concatenate(List<String> urls) throws InvalidURLException {
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

    private static BufferedImage toImage(String urlString) throws InvalidURLException {
        URL url;

        try {
            url = new URL(urlString);
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new InvalidURLException(urlString, e);
        }
    }

    private static List<BufferedImage> toImages(List<String> urls) throws InvalidURLException {
        return urls.stream().map(Orientation::toImage).collect(Collectors.toList());
    }

    private static int getMaxHeight(List<BufferedImage> images) {
        return Collections.max(images, Comparator.comparingInt(BufferedImage::getHeight)).getHeight();
    }

    private static int getTotalHeight(List<BufferedImage> images) {
        return images.stream().mapToInt(BufferedImage::getHeight).sum();
    }

    private static int getMaxWidth(List<BufferedImage> images) {
        return Collections.max(images, Comparator.comparingInt(BufferedImage::getWidth)).getWidth();
    }

    private static int getTotalWidth(List<BufferedImage> images) {
        return images.stream().mapToInt(BufferedImage::getWidth).sum();
    }

    public abstract BufferedImage concatenate(List<String> urls) throws InvalidURLException;

}
