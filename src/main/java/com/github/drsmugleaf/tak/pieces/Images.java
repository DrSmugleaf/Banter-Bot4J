package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.BanterBot4J;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 02/01/2019
 */
public class Images {

    @Nonnull
    private static final String IMAGES_PATH = Objects.requireNonNull(Images.class.getClassLoader().getResource("tak/pieces")).getFile();

    @NotNull
    private static final Map<Color, Map<Type, Image>> IMAGES = registerAll();

    private Images() {}

    @NotNull
    private static Map<Color, Map<Type, Image>> registerAll() {
        Map<Color, Map<Type, Image>> images = new EnumMap<>(Color.class);

        for (Color color : Color.values()) {
            images.put(color, new EnumMap<>(Type.class));

            for (Type type : Type.values()) {
                String filePath = IMAGES_PATH + "/" + color.getFolderName() + type.getFileName();
                try (FileInputStream stream = new FileInputStream(filePath)) {
                    BufferedImage image = ImageIO.read(stream);
                    images.get(color).put(type, image);
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
        }

        return images;
    }

    @NotNull
    public static Image getImage(@NotNull Piece piece) {
        return IMAGES.get(piece.getColor()).get(piece.getType());
    }

    @NotNull
    public static Image getImage(@NotNull Piece piece, int height, int width) {
        return getImage(piece).getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

}
