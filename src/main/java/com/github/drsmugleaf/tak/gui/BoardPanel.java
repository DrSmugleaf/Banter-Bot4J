package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.IPreset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class BoardPanel extends Board {

    private final JPanel GUI = new JPanel(new BorderLayout(3, 3));
    private final JPanel BOARD;
    private final SquareButton[][] BUTTONS;

    private BoardPanel(IPreset preset, SquareButton[][] squares) {
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

    public static BoardPanel from(IPreset preset) {
        int dimensions = preset.getSize();
        SquareButton[][] board = new SquareButton[dimensions][dimensions];
        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            SquareButton[] row = board[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                board[rowIndex][columnIndex] = new SquareButton(rowIndex, columnIndex, preset);
            }
        }

        return new BoardPanel(preset, board);
    }

    protected JPanel getGui() {
        return GUI;
    }

    protected JPanel getBoard() {
        return BOARD;
    }

    protected SquareButton[][] getButtons() {
        return BUTTONS;
    }

}
