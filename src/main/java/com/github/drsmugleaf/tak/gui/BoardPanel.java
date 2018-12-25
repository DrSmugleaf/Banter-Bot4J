package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.Preset;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class BoardPanel {

    public final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private final JButton[][] pieces;
    public final JPanel board;

    BoardPanel(@NotNull Preset preset) {
        int dimensions = preset.getSize();
        pieces = new JButton[dimensions][dimensions];
        board = new JPanel(new GridLayout(0, dimensions));

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        board.setBorder(new LineBorder(Color.BLACK));
        gui.add(board);

        Insets margin = new Insets(0, 0, 0, 0);
        int squareSize = 800 / dimensions;
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                JButton button = new JButton();
                button.setMargin(margin);
                ImageIcon icon = new ImageIcon(new BufferedImage(squareSize, squareSize, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(icon);

                if ((i + j) % 2 == 0) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.GRAY);
                }

                pieces[i][j] = button;
                board.add(pieces[i][j]);
            }
        }
    }

}
