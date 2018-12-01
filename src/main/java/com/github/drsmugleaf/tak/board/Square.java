package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square {

    @NotNull
    private final List<Piece> PIECES = new ArrayList<>();

    Square() {}

    @Nullable
    private Piece getTop() {
        if (PIECES.isEmpty()) {
            return null;
        }

        return PIECES.get(PIECES.size() - 1);
    }

    @NotNull
    public List<Piece> getPieces() {
        return new ArrayList<>(PIECES);
    }

    public boolean canPlace(@NotNull Type type) {
        Piece topPiece = getTop();
        return topPiece == null || type.ignoresBlock() || !topPiece.getType().blocksTop();
    }

    public void place(@NotNull Piece piece) {
        PIECES.add(piece);
    }

}
