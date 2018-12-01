package com.github.drsmugleaf.tak.pieces;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Type {

    FLAT_STONE(false, false),
    STANDING_STONE(true, false),
    CAPSTONE(true, true);

    private final boolean BLOCKS_TOP;
    private final boolean IGNORES_BLOCK;

    Type(boolean blocksTop, boolean ignoresBlock) {
        BLOCKS_TOP = blocksTop;
        IGNORES_BLOCK = ignoresBlock;
    }

    public boolean blocksTop() {
        return BLOCKS_TOP;
    }

    public boolean ignoresBlock() {
        return IGNORES_BLOCK;
    }

}
