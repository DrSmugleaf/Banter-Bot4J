package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.board.ISquare;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Type {

    CAPSTONE("capstone.png", true, true, true, 1, "C") {
        @Override
        public boolean canMoveTo(Piece topPiece) {
            return topPiece.getType() != CAPSTONE;
        }

        @Override
            public void move(ISquare to, int pieces) {
            Piece topPiece = to.getTopPiece();
            if (pieces == 1 && topPiece != null && topPiece.getType() == STANDING_STONE) {
                topPiece.flatten();
            }
        }
    },
    FLAT_STONE("flat.png", false, false, true, 2, "F"),
    STANDING_STONE("wall.png", true, false, false, 3, "S");

    private static final ImmutableSet<Type> TYPES = ImmutableSet.copyOf(values());
    private final String FILE_NAME;
    private final boolean BLOCKS;
    private final boolean IGNORES_BLOCK;
    private final boolean FORMS_ROAD;
    private final double VALUE;
    private final String STRING;

    Type(String fileName, boolean blocks, boolean ignoresBlock, boolean formsRoad, double value, String string) {
        FILE_NAME = fileName;
        BLOCKS = blocks;
        IGNORES_BLOCK = ignoresBlock;
        FORMS_ROAD = formsRoad;
        VALUE = value;
        STRING = string;
    }

    public static ImmutableSet<Type> getTypes() {
        return TYPES;
    }

    public String getFileName() {
        return FILE_NAME;
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

    public double toDouble() {
        return VALUE;
    }

    @Override
    public String toString() {
        return STRING;
    }

    public boolean canMoveTo(Piece topPiece) {
        return ignoresBlock() || !topPiece.getType().blocks();
    }

    public void move(ISquare to, int pieces) {}

}
