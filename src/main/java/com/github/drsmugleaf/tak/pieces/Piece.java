package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.tak.board.Preset;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Piece {

    @Nonnull
    private static final String IMAGES_PATH = Objects.requireNonNull(Preset.class.getClassLoader().getResource("tak/pieces")).getFile();

    @NotNull
    private final Color COLOR;

    @NotNull
    private Type TYPE;

    public Piece(@NotNull Color color, @NotNull Type type) {
        COLOR = color;
        TYPE = type;
    }

    @NotNull
    public Color getColor() {
        return COLOR;
    }

    @NotNull
    public Type getType() {
        return TYPE;
    }

    public void flatten() {
        if (getType() != Type.STANDING_STONE) {
            throw new IllegalStateException("Piece isn't a standing stone");
        }

        TYPE = Type.FLAT_STONE;
    }

    @Override
    public String toString() {
        return COLOR.toString();
    }

    @NotNull
    public InputStream toImage() {
        StringBuilder builder = new StringBuilder(IMAGES_PATH).append("/");
        switch (getColor()) {
            case BLACK:
                builder.append("black/");
                break;
            case WHITE:
                builder.append("white/");
                break;
            default:
                throw new IllegalStateException("Unrecognized piece color " + getColor());
        }

        switch (getType()) {
            case FLAT_STONE:
                builder.append("flat.png");
                break;
            case STANDING_STONE:
                builder.append("wall.png");
                break;
            case CAPSTONE:
                builder.append("capstone.png");
                break;
            default:
                throw new IllegalStateException("Unrecognized piece type " + getType());
        }

        String filePath = builder.toString();
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("No image found in path " + filePath, e);
            throw new IllegalStateException("No image found in path " + filePath, e);
        }
    }

    @NotNull
    public Image toImage(int height, int width) {
        BufferedImage image;
        try {
            image = ImageIO.read(toImage());
        } catch (IOException e) {
            throw new RuntimeException("Error reading image input stream", e);
        }

        return image.getScaledInstance(width, height, Image.SCALE_FAST);
    }

}
