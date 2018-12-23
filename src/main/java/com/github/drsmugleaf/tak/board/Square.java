package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.dijkstra.Node;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square extends Node<Square> {

    @NotNull
    private final List<Piece> PIECES = new ArrayList<>();

    Square() {}

    @NotNull
    public List<Piece> getPieces() {
        return new ArrayList<>(PIECES);
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

    public void place(@NotNull Piece piece) {
        PIECES.add(piece);
    }

    public boolean connectsTo(@Nullable Piece piece) {
        if (piece == null || getTopPiece() == null) {
            return false;
        }

        return getTopPiece().getColor() == piece.getColor() && getTopPiece().getType().formsRoad() && piece.getType().formsRoad();
    }

    public boolean connectsTo(@Nullable Square square) {
        if (square == null) {
            return false;
        }

        return connectsTo(square.getTopPiece());
    }

}
