package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square implements ISquare {

    private final List<Piece> PIECES = new ArrayList<>();
    private final int COLUMN;
    private final int ROW;

    protected Square(int column, int row) {
        COLUMN = column;
        ROW = row;
    }

    private Square(ISquare square) {
        this(square.getColumn(), square.getRow());

        for (Piece piece : square.getPieces()) {
            PIECES.add(piece.copy());
        }
    }

    public static ISquare createCustom(int column, int row, Piece... pieces) {
        ISquare square = new Square(column, row);
        Collections.addAll(square.getPieces(), pieces);
        return square;
    }

    @Override
    public ISquare copy() {
        return new Square(this);
    }

    @Override
    public int getColumn() {
        return COLUMN;
    }

    @Override
    public int getRow() {
        return ROW;
    }

    @Override
    public List<Piece> getPieces() {
        return PIECES;
    }

    @Nullable
    @Override
    public Color getColor() {
        if (getTopPiece() == null) {
            return null;
        }

        return getTopPiece().getColor();
    }

    @Nullable
    @Override
    public Type getType() {
        if (getTopPiece() == null) {
            return null;
        }

        return getTopPiece().getType();
    }

    @Nullable
    @Override
    public Piece getTopPiece() {
        if (PIECES.isEmpty()) {
            return null;
        }

        return PIECES.get(PIECES.size() - 1);
    }

    @Override
    public boolean canMove(ISquare other, int pieces) {
        if (pieces <= 0 || pieces > PIECES.size()) {
            return false;
        }

        Piece thisTopPiece = getTopPiece();
        if (thisTopPiece == null) {
            return false;
        }

        Piece otherTopPiece = other.getTopPiece();
        if (otherTopPiece == null) {
            return true;
        }

        if (pieces == 1) {
            return thisTopPiece.getType().canMoveTo(otherTopPiece);
        } else {
            return !otherTopPiece.getType().blocks();
        }
    }

    @Override
    public ISquare move(ISquare destination, int pieces, boolean silent) {
        ListIterator<Piece> iterator = PIECES.listIterator(PIECES.size());
        while (iterator.hasPrevious() && pieces > 0) {
            Piece piece = iterator.previous();

            piece.getType().move(destination, pieces);
            iterator.remove();
            destination.getPieces().add(piece);

            pieces--;
        }

        if (!silent) {
            onUpdate();
            destination.onUpdate();
        }

        return this;
    }

    @Override
    public boolean canPlace() {
        return getTopPiece() == null;
    }

    @Override
    public ISquare place(Piece piece, boolean silent) {
        PIECES.add(piece);

        if (!silent) {
            onUpdate();
        }

        return this;
    }

    @Override
    public ISquare remove(Piece piece, boolean silent) {
        PIECES.remove(piece);

        if (!silent) {
            onUpdate();
        }

        return this;
    }

    @Override
    public boolean connectsTo(@Nullable Piece piece) {
        Piece topPiece = getTopPiece();
        if (piece == null || topPiece == null) {
            return false;
        }

        return getColor() == piece.getColor() && topPiece.getType().formsRoad() && piece.getType().formsRoad();
    }

    @Override
    public boolean connectsTo(@Nullable ISquare square) {
        if (square == null) {
            return false;
        }

        return connectsTo(square.getTopPiece());
    }

    @Override
    public void onUpdate() {}

    @Override
    public void reset() {
        PIECES.clear();
    }

    @Override
    public double[] toDoubleArray(int totalPieces) {
        double[] array = new double[totalPieces];
        for (int i = 0; i < PIECES.size(); i++) {
            array[i] = PIECES.get(i).toDouble();
        }

        return array;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<Piece> pieces = getPieces();

        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            builder.append(piece);

            if (i < pieces.size() - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
