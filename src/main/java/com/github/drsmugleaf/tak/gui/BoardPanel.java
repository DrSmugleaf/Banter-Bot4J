package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.Preset;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class BoardPanel {

    protected final JPanel gui = new JPanel(new BorderLayout(3, 3));
    protected final SquareButton[][] pieces;
    protected final JPanel board;

    protected BoardPanel(@NotNull Preset preset) {
        int dimensions = preset.getSize();
        pieces = SquareButton.fromPreset(preset);
        board = new JPanel(new GridLayout(0, dimensions));

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        board.setBorder(new LineBorder(Color.BLACK));
        gui.add(board);

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                board.add(pieces[i][j].toJButton());
            }
        }
    }

}
