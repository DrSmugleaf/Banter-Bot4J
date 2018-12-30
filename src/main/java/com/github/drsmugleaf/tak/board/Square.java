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

    private final int ROW;
    private final int COLUMN;

    Square(int row, int column) {
        ROW = row;
        COLUMN = column;
    }

    private Square(@NotNull Square square) {
        this(square.getRow(), square.getColumn());
        PIECES.addAll(square.getPieces());
    }

    @NotNull
    public Square copy() {
        return new Square(this);
    }

    public int getRow() {
        return ROW;
    }

    public int getColumn() {
        return COLUMN;
    }

    @NotNull
    public static Square createCustom(int row, int column, @NotNull Piece... pieces) {
        Square square = new Square(row, column);
        Collections.addAll(square.PIECES, pieces);
        return square;
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

    public boolean canPlace(@NotNull Type type) {
        Piece topPiece = getTopPiece();
        return topPiece == null || type.ignoresBlock() || !topPiece.getType().blocks();
    }

    @NotNull
    public Square place(@NotNull Piece piece) {
        PIECES.add(piece);
        return this;
    }

    @NotNull
    public Square remove(@NotNull Piece piece) {
        PIECES.remove(piece);
        return this;
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
