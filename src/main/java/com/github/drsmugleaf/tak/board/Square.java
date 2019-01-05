package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square {

    @NotNull
    private final List<Piece> PIECES = new ArrayList<>();

    private final int COLUMN;
    private final int ROW;

    protected Square(int column, int row) {
        COLUMN = column;
        ROW = row;
    }

    private Square(@NotNull Square square) {
        this(square.getColumn(), square.getRow());

        for (Piece piece : square.getPieces()) {
            PIECES.add(piece.copy());
        }
    }

    @NotNull
    public static Square createCustom(int column, int row, @NotNull Piece... pieces) {
        Square square = new Square(column, row);
        Collections.addAll(square.PIECES, pieces);
        return square;
    }

    @NotNull
    public Square copy() {
        return new Square(this);
    }

    public int getColumn() {
        return COLUMN;
    }

    public int getRow() {
        return ROW;
    }

    @NotNull
    public List<Piece> getPieces() {
        return new ArrayList<>(PIECES);
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

    public boolean canMove(@NotNull Square other, int pieces) {
        if (!isAdjacentTo(other)) {
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

    public boolean canPlace() {
        return getTopPiece() == null;
    }

    @NotNull
    public Square place(@NotNull Piece piece) {
        piece.getType().place(this, PIECES, piece);
        return this;
    }

    @NotNull
    public Square remove(@NotNull Piece piece) {
        PIECES.remove(piece);
        return this;
    }

    public boolean isAdjacentTo(@NotNull Square other) {
        int otherColumn = other.getColumn();
        int otherRow = other.getRow();

        return (getColumn() - otherColumn == 1 || otherColumn - getColumn() == 1) &&
               (getRow() - otherRow == 1 || otherRow - getRow() == 1);
    }

    public boolean connectsTo(@Nullable Piece piece) {
        if (piece == null || getTopPiece() == null) {
            return false;
        }

        return getColor() == piece.getColor() && getTopPiece().getType().formsRoad() && piece.getType().formsRoad();
    }

    public boolean connectsTo(@Nullable Square square) {
        if (square == null) {
            return false;
        }

        return connectsTo(square.getTopPiece());
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<Piece> pieces = getPieces();

        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            builder.append(piece.getColor());

            if (i < pieces.size() - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
