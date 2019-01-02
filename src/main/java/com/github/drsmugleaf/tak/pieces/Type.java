package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.board.Square;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Type {

    CAPSTONE("capstone.png", true, true, true) {
        @Override
        public boolean ignoresBlock(@NotNull Piece topPiece) {
            return topPiece.getType() != CAPSTONE;
        }

        @Override
        public void place(@NotNull Square square, List<Piece> pieces, @NotNull Piece piece) {
            Piece topPiece = square.getTopPiece();
            if (topPiece != null && topPiece.getType() == STANDING_STONE) {
                topPiece.flatten();
            }

            pieces.add(piece);
        }
    },
    FLAT_STONE("flat.png", false, false, true),
    STANDING_STONE("wall.png", true, false, false);

    @NotNull
    private final String FILE_NAME;
    private final boolean BLOCKS;
    private final boolean IGNORES_BLOCK;
    private final boolean FORMS_ROAD;

    Type(@NotNull String fileName, boolean blocks, boolean ignoresBlock, boolean formsRoad) {
        FILE_NAME = fileName;
        BLOCKS = blocks;
        IGNORES_BLOCK = ignoresBlock;
        FORMS_ROAD = formsRoad;
    }

    @NotNull
    protected String getFileName() {
        return FILE_NAME;
    }

    public boolean blocks() {
        return BLOCKS;
    }

    public boolean ignoresBlock(@NotNull Piece topPiece) {
        return IGNORES_BLOCK;
    }

    public boolean formsRoad() {
        return FORMS_ROAD;
    }

    public void place(@NotNull Square square, List<Piece> pieces, @NotNull Piece piece) {
        pieces.add(piece);
    }

}
