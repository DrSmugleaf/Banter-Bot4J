package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.board.layout.Row;
import com.github.drsmugleaf.tak.pieces.IPiece;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public class BoardState implements IBoardState {

    private final int ID;
    private final IPiece[][][] PIECES;

    public BoardState(int id, @Nullable IBoardState previousState, IBoard current) {
        ID = id;
        IPreset preset = current.getPreset();
        int size = preset.getSize();
        int maxStack = preset.getMaximumStackSize();
        PIECES = new IPiece[size][size][maxStack];
        if (previousState == null) {
            for (Row row : current.getRows()) {
                for (ISquare square : row.getSquares()) {
                    int rowIndex = square.getRow();
                    int columnIndex = square.getColumn();
                    PIECES[rowIndex][columnIndex] = square.getPieces().toArray(new IPiece[]{});
                }
            }
        } else {
            IPiece[][][] previous = previousState.getPieces();
            for (int rowIndex = 0; rowIndex < previous.length; rowIndex++) {
                IPiece[][] rows = previous[rowIndex];
                for (int columnIndex = 0; columnIndex < rows.length; columnIndex++) {
                    IPiece[] previousStack = rows[columnIndex];
                    ISquare currentSquare = current.getRows()[rowIndex].getSquares()[columnIndex];
                    List<IPiece> currentStack = currentSquare.getPieces();
                    long previousPieces = Arrays.stream(previousStack).filter(Objects::nonNull).count();
                    if (previousPieces == currentStack.size()) {
                        continue;
                    }

                    PIECES[rowIndex][columnIndex] = currentStack.toArray(new IPiece[]{});
                }
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
