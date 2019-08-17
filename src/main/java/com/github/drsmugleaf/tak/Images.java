package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 02/01/2019
 */
public class Images {

    private static final String IMAGES_PATH = Objects.requireNonNull(Images.class.getClassLoader().getResource("tak")).getFile();
    private static final Image LOGO = registerLogo();
    private static final Map<com.github.drsmugleaf.tak.pieces.Color, Map<Type, Image>> PIECES = registerPieces();
    private static final Map<String, Image> SQUARES = registerSquares();

    private Images() {}

    private static Image registerLogo() {
        String filePath = IMAGES_PATH + "/logo.jpg";
        try (FileInputStream stream = new FileInputStream(filePath)) {
            return ImageIO.read(stream);
        } catch (FileNotFoundException e) {
            String message = "Tak logo not found in path " + filePath;
            BanterBot4J.warn(message, e);
            throw new IllegalStateException(message, e);
        } catch (IOException e) {
            String message = "Error reading tak logo in path" + filePath;
            BanterBot4J.warn(message, e);
            throw new IllegalStateException(message, e);
        }
    }

    private static Map<com.github.drsmugleaf.tak.pieces.Color, Map<Type, Image>> registerPieces() {
        Map<com.github.drsmugleaf.tak.pieces.Color, Map<Type, Image>> images = new EnumMap<>(com.github.drsmugleaf.tak.pieces.Color.class);

        for (com.github.drsmugleaf.tak.pieces.Color color : Color.values()) {
            images.put(color, new EnumMap<>(Type.class));

            for (Type type : Type.getTypes()) {
                String filePath = IMAGES_PATH + "/pieces/" + color.getFolderName() + type.getFileName();
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

    private static Map<String, Image> registerSquares() {
        Map<String, Image> images = new HashMap<>();

        String filePath = IMAGES_PATH + "/squares";
        File[] squares = new File(filePath).listFiles(File::isFile);
        if (squares == null) {
            String message = "Error reading files in path " + filePath;
            BanterBot4J.warn(message);
            throw new IllegalStateException(message);
        }

        for (File square : squares) {
            try {
                BufferedImage image = ImageIO.read(square);
                images.put(square.getName(), image);
            } catch (IOException e) {
                String message = "Error reading square image file " + square.getAbsolutePath();
                BanterBot4J.warn(message, e);
                throw new UncheckedIOException(message, e);
            }
        }

        return images;
    }

    public static Image getLogo() {
        return LOGO;
    }

    public static Image getPiece(Piece piece) {
        return PIECES.get(piece.getColor()).get(piece.getType());
    }

    public static Image getPiece(Piece piece, int height, int width) {
        return getPiece(piece).getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static Image getSquare(int column, int row) {
        String file;
        if ((column + row) % 2 == 0) {
            file = "white square.jpg";
        } else {
            file = "orange square.jpg";
        }

        return SQUARES.get(file);
    }

}
