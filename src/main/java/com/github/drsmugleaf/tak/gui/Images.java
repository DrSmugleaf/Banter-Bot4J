package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.BanterBot4J;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 06/01/2019
 */
public class Images {

    @NotNull
    private static final String IMAGES_PATH = Objects.requireNonNull(Images.class.getClassLoader().getResource("tak")).getFile();

    @NotNull
    private static final Map<String, Image> IMAGES = new HashMap<>();

    private Images() {}

    @NotNull
    public static Image get(@NotNull String file) {
        Image image = IMAGES.get(file);
        if (image != null) {
            return image;
        }

        String filePath = IMAGES_PATH + "/" + file;
        try (FileInputStream stream = new FileInputStream(filePath)) {
            image = ImageIO.read(stream);
            return image;
        } catch (FileNotFoundException e) {
            String message = "No image found in path " + filePath;
            BanterBot4J.warn(message, e);
            throw new IllegalStateException(message, e);
        } catch (IOException e) {
            String message = "Error reading image in path " + filePath;
            BanterBot4J.warn(message, e);
            throw new IllegalStateException(message, e);
        }
    }

    public static ImageIcon getIcon(@NotNull String file) {
        Image image = get(file);
        return new ImageIcon(image);
    }

}
