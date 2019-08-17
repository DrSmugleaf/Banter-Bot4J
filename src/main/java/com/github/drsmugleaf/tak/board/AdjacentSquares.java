package com.github.drsmugleaf.tak.board;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 02/12/2018
 */
public class AdjacentSquares {

    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private final Square CENTER;
    private final List<Square> ADJACENT = new ArrayList<>();

    protected AdjacentSquares(Square center, @Nullable Square up, @Nullable Square right, @Nullable Square down, @Nullable Square left) {
        CENTER = center;
        ADJACENT.add(UP, up);
        ADJACENT.add(RIGHT, right);
        ADJACENT.add(DOWN, down);
        ADJACENT.add(LEFT, left);
    }

    public boolean contains(Square square) {
        return ADJACENT.contains(square);
    }

    public List<Square> getAll() {
        return ADJACENT;
    }

    public List<Square> getConnections() {
        List<Square> squares = new ArrayList<>(getAll());
        squares.removeIf(square -> !getCenter().connectsTo(square));
        return squares;
    }

    public Square getCenter() {
        return CENTER;
    }

    @Nullable
    public Square getUp() {
        return ADJACENT.get(UP);
    }

    @Nullable
    public Square getRight() {
        return ADJACENT.get(RIGHT);
    }

    @Nullable
    public Square getDown() {
        return ADJACENT.get(DOWN);
    }

    @Nullable
    public Square getLeft() {
        return ADJACENT.get(LEFT);
    }

}
