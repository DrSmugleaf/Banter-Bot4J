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
public class SquareButton extends Square {

    private static final int SINGLE_SQUARE_SIZE = 800;

    @NotNull
    private static final Insets margin = new Insets(0, 0, 0, 0);

    private final int SIZE;

    @NotNull
    private final JButton BUTTON;

    protected SquareButton(int column, int row, @NotNull Preset preset) {
        super(column, row);
        SIZE = SINGLE_SQUARE_SIZE / preset.getSize();

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
    private Image getDefaultImage() {
        String file;
        if ((getColumn() + getRow()) % 2 == 0) {
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

    @Override
    protected void onUpdate() {
        List<Piece> pieces = getPieces();
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
    }

}
