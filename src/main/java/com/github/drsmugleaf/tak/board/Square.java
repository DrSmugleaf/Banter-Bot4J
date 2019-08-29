package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square {

    private final List<Piece> PIECES = new ArrayList<>();
    private final int COLUMN;
    private final int ROW;

    protected Square(int column, int row) {
        COLUMN = column;
        ROW = row;
    }

    private Square(Square square) {
        this(square.getColumn(), square.getRow());

        for (Piece piece : square.getPieces()) {
            PIECES.add(piece.copy());
        }
    }

    public static Square createCustom(int column, int row, Piece... pieces) {
        Square square = new Square(column, row);
        Collections.addAll(square.PIECES, pieces);
        return square;
    }

    public Square copy() {
        return new Square(this);
    }

    public int getColumn() {
        return COLUMN;
    }

    public int getRow() {
        return ROW;
    }

    public List<Piece> getPieces() {
        return new ArrayList<>(PIECES);
    }

    public double[] toDoubleArray(int totalPieces) {
        double[] array = new double[totalPieces];
        for (int i = 0; i < PIECES.size(); i++) {
            array[i] = PIECES.get(i).toDouble();
        }

        return array;
    }

    @Nullable
    public Color getColor() {
        if (getTopPiece() == null) {
            return null;
        }

        return getTopPiece().getColor();
    }

    @Nullable
    public Type getType() {
        if (getTopPiece() == null) {
            return null;
        }

        return getTopPiece().getType();
    }

    @Nullable
    public Piece getTopPiece() {
        if (PIECES.isEmpty()) {
            return null;
        }

        return PIECES.get(PIECES.size() - 1);
    }

    public boolean canMove(Square other, int pieces) {
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

        if (pieces != 1 && otherTopPiece.getType().blocks()) {
            return false;
        }

        return pieces == 1 && thisTopPiece.getType().canMoveTo(otherTopPiece);
    }

    public Square move(Square destination, int pieces, boolean silent) {
        for (int i = pieces - 1; i >= 0; i--) {
            Piece piece = PIECES.remove(i);
            destination.PIECES.add(piece);
        }

        if (!silent) {
            onUpdate();
            destination.onUpdate();
        }

        return this;
    }

    public boolean canPlace() {
        return getTopPiece() == null;
    }

    public Square place(Piece piece, boolean silent) {
        piece.getType().place(this, PIECES, piece);

        if (!silent) {
            onUpdate();
        }

        return this;
    }

    public Square remove(Piece piece, boolean silent) {
        PIECES.remove(piece);

        if (!silent) {
            onUpdate();
        }

        return this;
    }

    public boolean connectsTo(@Nullable Piece piece) {
        Piece topPiece = getTopPiece();
        if (piece == null || topPiece == null) {
            return false;
        }

        return getColor() == piece.getColor() && topPiece.getType().formsRoad() && piece.getType().formsRoad();
    }

    public boolean connectsTo(@Nullable Square square) {
        if (square == null) {
            return false;
        }

        return connectsTo(square.getTopPiece());
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

    protected void onUpdate() {}

    public void reset() {
        PIECES.clear();
    }

}
