package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Preset;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class BoardPanel extends Board {

    @NotNull
    private final JPanel GUI = new JPanel(new BorderLayout(3, 3));

    @NotNull
    private final JPanel BOARD;

    @NotNull
    private final SquareButton[][] BUTTONS;

    private BoardPanel(@NotNull Preset preset, @NotNull SquareButton[][] squares) {
        super(squares);
        BUTTONS = squares;

        int dimensions = preset.getSize();
        BOARD = new JPanel(new GridLayout(0, dimensions));

        GUI.setBorder(new EmptyBorder(5, 5, 5, 5));
        BOARD.setBorder(new LineBorder(Color.BLACK));
        GUI.add(BOARD);

        for (SquareButton[] column : squares) {
            for (SquareButton square : column) {
                BOARD.add(square.getButton());
            }
        }
    }

    @NotNull
    public static BoardPanel from(@NotNull Preset preset) {
        int dimensions = preset.getSize();
        SquareButton[][] board = new SquareButton[dimensions][dimensions];
        for (int i = 0; i < board.length; i++) {
            SquareButton[] row = board[i];
            for (int j = 0; j < row.length; j++) {
                board[i][j] = new SquareButton(i, j, preset);
            }
        }

        return new BoardPanel(preset, board);
    }

    @NotNull
    protected JPanel getGui() {
        return GUI;
    }

    @NotNull
    protected JPanel getBoard() {
        return BOARD;
    }

    @NotNull
    protected SquareButton[][] getButtons() {
        return BUTTONS;
    }

}
