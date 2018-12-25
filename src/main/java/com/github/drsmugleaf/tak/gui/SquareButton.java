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

    protected SquareButton(int size, @NotNull Color color) {
        SIZE = size;
        JButton button = new JButton();
        button.setMargin(margin);
        ImageIcon icon = getDefaultImage();
        button.setIcon(icon);
        button.setBackground(color);
        BUTTON = button;
    }

    @NotNull
    protected static SquareButton[][] fromPreset(@NotNull Preset preset) {
        int size = preset.getSize();
        SquareButton[][] squares = new SquareButton[size][size];

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                Color color;
                if ((i + j) % 2 == 0) {
                    color = Color.WHITE;
                } else {
                    color = Color.GRAY;
                }

                squares[i][j] = new SquareButton(SINGLE_SQUARE_SIZE / size, color);
            }
        }

        return squares;
    }

    @NotNull
    private ImageIcon getDefaultImage() {
        return new ImageIcon(new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB));
    }

    @NotNull
    protected JButton toJButton() {
        return BUTTON;
    }

    @NotNull
    protected JButton update(@NotNull Square square) {
        List<Piece> pieces = square.getPieces();
        Image background = getDefaultImage().getImage();
        Graphics g = background.getGraphics();
        int backgroundHeight = background.getHeight(null);
        int backgroundWidth = background.getWidth(null);

        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            int pieceSize = SIZE - 20 * i;
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
