package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class SquareButton {

    private static final int SINGLE_SQUARE_SIZE = 800;

    @NotNull
    private static final Insets margin = new Insets(0, 0, 0, 0);

    private final int SIZE;

    @NotNull
    private final JButton BUTTON;

    private final int ROW;

    private final int COLUMN;

    protected SquareButton(int size, int column, int row) {
        SIZE = size;
        COLUMN = column;
        ROW = row;

        JButton button = new JButton();
        button.setMargin(margin);
        ImageIcon icon = new ImageIcon(getDefaultImage());
        button.setIcon(icon);

        BUTTON = button;
    }

    @NotNull
    protected JButton getButton() {
        return BUTTON;
    }

    @NotNull
    protected static SquareButton[][] fromPreset(@NotNull Preset preset) {
        int size = preset.getSize();
        SquareButton[][] squares = new SquareButton[size][size];

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = new SquareButton(SINGLE_SQUARE_SIZE / size, j, i);
            }
        }

        return squares;
    }

    @NotNull
    private Image getDefaultImage() {
        String file;
        if ((COLUMN + ROW) % 2 == 0) {
            file = "squares/white square.jpg";
        } else {
            file = "squares/orange square.jpg";
        }

        Image image = Images.get(file).getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
        BufferedImage background = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = background.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return background;
    }

    @NotNull
    protected JButton toJButton() {
        return BUTTON;
    }

    @NotNull
    protected JButton update(@NotNull Square square) {
        List<Piece> pieces = square.getPieces();
        Image background = getDefaultImage();
        Graphics g = background.getGraphics();
        int backgroundHeight = background.getHeight(null);
        int backgroundWidth = background.getWidth(null);

        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            int pieceSize = SIZE - 20 * i;
            if (pieceSize < 10) {
                pieceSize = 10;
            }

            Image image = piece.toImage(pieceSize, pieceSize);

            g.drawImage(
                    image,
                    (backgroundWidth - image.getWidth(null)) / 2,
                    (backgroundHeight - image.getHeight(null)) / 2,
                    null
            );
        }

        g.dispose();

        BUTTON.setIcon(new ImageIcon(background));

        return toJButton();
    }

}
