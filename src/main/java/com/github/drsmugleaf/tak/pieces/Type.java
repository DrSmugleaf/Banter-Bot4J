package com.github.drsmugleaf.tak.pieces;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Type {

    FLAT_STONE(false, false, true),
    STANDING_STONE(true, false, false),
    CAPSTONE(true, true, true); // TODO: Don't flatten another capstone?

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

}
