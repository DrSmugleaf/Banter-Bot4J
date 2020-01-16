package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.board.layout.Row;
import com.github.drsmugleaf.tak.pieces.IPiece;

import java.util.List;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public class BoardState implements IBoardState {

    private final int ID;
    private final IPiece[][][] PIECES;

    public BoardState(int id, IBoard current) {
        ID = id;
        IPreset preset = current.getPreset();
        int size = preset.getSize();
        int maxStack = preset.getMaximumStackSize();
        PIECES = new IPiece[size][size][maxStack];
        Row[] rows = current.getRows();
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            Row row = rows[rowIndex];
            ISquare[] column = row.getSquares();
            for (int columnIndex = 0; columnIndex < column.length; columnIndex++) {
                ISquare square = column[columnIndex];
                List<IPiece> stack = square.getPieces();
                PIECES[rowIndex][columnIndex] = stack.toArray(new IPiece[]{});
            }
        }
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public IPiece[][][] getPieces() {
        return PIECES;
    }

}
