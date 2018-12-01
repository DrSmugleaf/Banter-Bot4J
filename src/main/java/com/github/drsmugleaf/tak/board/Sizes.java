package com.github.drsmugleaf.tak.board;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Sizes {

    THREE(3, 0, 10),
    FOUR(4, 0, 15),
    FIVE(5, 1, 21),
    SIX(6, 1, 30),
    EIGHT(8, 2, 50);

    private final int SIZE;
    private final int STONES;
    private final int CAPSTONES;

    Sizes(int size, int capstones, int stones) {
        SIZE = size;
        STONES = stones;
        CAPSTONES = capstones;
    }

    public int getSize() {
        return SIZE;
    }

    public int getStones() {
        return STONES;
    }

    public int getCapstones() {
        return CAPSTONES;
    }
}
