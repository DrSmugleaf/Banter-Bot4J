package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 02/12/2018
 */
public class AdjacentSquares implements IAdjacentSquares {

    private final ISquare CENTER;
    @Nullable
    private final ISquare UP;
    @Nullable
    private final ISquare RIGHT;
    @Nullable
    private final ISquare DOWN;
    @Nullable
    private final ISquare LEFT;
    private final ImmutableSet<ISquare> ADJACENT;
    private final ImmutableSet<ISquare> CONNECTIONS;

    protected AdjacentSquares(
            ISquare center,
            @Nullable ISquare up,
            @Nullable ISquare right,
            @Nullable ISquare down,
            @Nullable ISquare left
    ) {
        CENTER = center;
        UP = up;
        RIGHT = right;
        DOWN = down;
        LEFT = left;
        ADJACENT = Stream
                .of(UP, RIGHT, DOWN, LEFT)
                .filter(Objects::nonNull)
                .collect(Collector.of(
                        ImmutableSet.Builder<ISquare>::new,
                        ImmutableSet.Builder<ISquare>::add,
                        (b1, b2) -> b1.addAll(b2.build()),
                        ImmutableSet.Builder<ISquare>::build
                ));
        CONNECTIONS = Stream
                .of(UP, RIGHT, DOWN, LEFT)
                .filter(Objects::nonNull)
                .filter(square -> square.connectsTo(CENTER))
                .collect(Collector.of(
                        ImmutableSet.Builder<ISquare>::new,
                        ImmutableSet.Builder<ISquare>::add,
                        (b1, b2) -> b1.addAll(b2.build()),
                        ImmutableSet.Builder<ISquare>::build
                ));
    }

    @Override
    public ISquare getCenter() {
        return CENTER;
    }

    @Override
    @Nullable
    public ISquare getUp() {
        return UP;
    }

    @Override
    @Nullable
    public ISquare getRight() {
        return RIGHT;
    }

    @Override
    @Nullable
    public ISquare getDown() {
        return DOWN;
    }

    @Override
    @Nullable
    public ISquare getLeft() {
        return LEFT;
    }

    @Override
    public boolean contains(ISquare square) {
        return ADJACENT.contains(square);
    }

    @Override
    public ImmutableSet<ISquare> getAll() {
        return ADJACENT;
    }

    @Override
    public ImmutableSet<ISquare> getConnections() {
        return CONNECTIONS;
    }

}
