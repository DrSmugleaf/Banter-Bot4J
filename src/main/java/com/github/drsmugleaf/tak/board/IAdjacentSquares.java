package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IAdjacentSquares {

    ISquare getOrigin();
    @Nullable
    ISquare getUp();
    @Nullable
    ISquare getRight();
    @Nullable
    ISquare getDown();
    @Nullable
    ISquare getLeft();
    boolean contains(ISquare square);
    ImmutableSet<ISquare> getAll();
    ImmutableSet<ISquare> getConnections();

}
