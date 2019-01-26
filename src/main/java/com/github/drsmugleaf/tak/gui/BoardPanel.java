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

    @NotNull
    protected final JPanel gui = new JPanel(new BorderLayout(3, 3));

    @NotNull
    protected final SquareButton[][] pieces;

    @NotNull
    protected final JPanel board;

    protected BoardPanel(@NotNull Preset preset) {
        int dimensions = preset.getSize();
        pieces = SquareButton.fromPreset(preset);
        board = new JPanel(new GridLayout(0, dimensions));

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        board.setBorder(new LineBorder(Color.BLACK));
        gui.add(board);

        for (SquareButton[] row : pieces) {
            for (SquareButton square : row) {
                board.add(square.getButton());
            }
        }
    }

}
