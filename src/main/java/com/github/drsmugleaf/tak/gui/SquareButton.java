package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.IPreset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.images.Images;
import com.github.drsmugleaf.tak.pieces.IPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class SquareButton extends Square {

    private static final int SINGLE_SQUARE_SIZE = 800;
    private static final Insets margin = new Insets(0, 0, 0, 0);
    private final int SIZE;
    private final JButton BUTTON;

    protected SquareButton(int row, int column, IPreset preset) {
        super(row, column);
        SIZE = SINGLE_SQUARE_SIZE / preset.getSize();

        JButton button = new JButton();
        button.setMargin(margin);
        ImageIcon icon = new ImageIcon(getDefaultImage());
        button.setIcon(icon);

        BUTTON = button;
    }

    protected JButton getButton() {
        return BUTTON;
    }

    private Image getDefaultImage() {
        Image image = Images.getSquare(getRow(), getColumn()).getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
        BufferedImage background = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = background.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        return background;
    }

    @Override
    public void onUpdate() {
        List<IPiece> pieces = getPieces();
        Image background = getDefaultImage();
        Graphics g = background.getGraphics();
        int backgroundHeight = background.getHeight(null);
        int backgroundWidth = background.getWidth(null);

        for (int i = 0; i < pieces.size(); i++) {
            IPiece piece = pieces.get(i);
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
