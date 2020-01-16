package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.coordinates.ICoordinates;

/**
 * Created by DrSmugleaf on 15/01/2020
 */
public interface IDirection {

    int getRowOffset();
    int getColumnOffset();
    boolean isValid(ICoordinates origin, ICoordinates destination);
    @Nullable
    ISquare get(IBoard board, ISquare origin);
    @Nullable
    ICoordinates get(int row, int column, int offset);

}
