package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.board.Square;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Type {

    FLAT_STONE(false, false, true) {
        @Override
        public void place(@NotNull Square square, List<Piece> pieces, @NotNull Piece piece) {
            pieces.add(piece);
        }
    },
    STANDING_STONE(true, false, false) {
        @Override
        public void place(@NotNull Square square, List<Piece> pieces, @NotNull Piece piece) {
            pieces.add(piece);
        }
    },
    CAPSTONE(true, true, true) {
        @Override
        public void place(@NotNull Square square, List<Piece> pieces, @NotNull Piece piece) {
            Piece topPiece = square.getTopPiece();
            if (topPiece != null && topPiece.getType() == STANDING_STONE) {
                topPiece.flatten();
            }

            pieces.add(piece);
        }
    }; // TODO: Don't flatten another capstone?

    private final boolean BLOCKS;
    private final boolean IGNORES_BLOCK;
    private final boolean FORMS_ROAD;

    Type(boolean blocks, boolean ignoresBlock, boolean formsRoad) {
        BLOCKS = blocks;
        IGNORES_BLOCK = ignoresBlock;
        FORMS_ROAD = formsRoad;
    }

    public boolean blocks() {
        return BLOCKS;
    }

    public boolean ignoresBlock() {
        return IGNORES_BLOCK;
    }

    public boolean formsRoad() {
        return FORMS_ROAD;
    }

    public abstract void place(@NotNull Square square, List<Piece> pieces, @NotNull Piece piece);

}
