package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.board.ISquare;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Type implements IType {

    CAPSTONE("capstone.png", true, true, true, 1, "C") {
        @Override
        public boolean canMoveTo(IPiece topPiece) {
            return topPiece.getType() != CAPSTONE;
        }

        @Override
            public void move(ISquare to, int pieces) {
            IPiece topPiece = to.getTopPiece();
            if (pieces == 1 && topPiece != null && topPiece.getType() == STANDING_STONE) {
                topPiece.flatten();
            }
        }
    },
    FLAT_STONE("flat.png", false, false, true, 2, "F"),
    STANDING_STONE("wall.png", true, false, false, 3, "S");

    private static final ImmutableSet<IType> TYPES = ImmutableSet.copyOf(values());
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

    public static ImmutableSet<IType> getTypes() {
        return TYPES;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    public boolean blocks() {
        return BLOCKS;
    }

    @Override
    public boolean ignoresBlock() {
        return IGNORES_BLOCK;
    }

    @Override
    public boolean formsRoad() {
        return FORMS_ROAD;
    }

    @Override
    public double toDouble() {
        return VALUE;
    }

    @Override
    public String toString() {
        return STRING;
    }

    @Override
    public boolean canMoveTo(IPiece topPiece) {
        return ignoresBlock() || !topPiece.getType().blocks();
    }

    @Override
    public void move(ISquare to, int pieces) {}

}
