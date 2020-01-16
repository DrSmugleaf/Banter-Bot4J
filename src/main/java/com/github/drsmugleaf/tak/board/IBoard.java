package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.board.history.IBoardHistory;
import com.github.drsmugleaf.tak.board.layout.*;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;

import java.util.List;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IBoard {

    IBoard copy();
    IBoard getDefault();
    Column[] getColumns();
    Row[] getRows();
    IPreset getPreset();
    IBoardHistory getHistory();
    boolean canMove(IMove move);
    void move(IMove move, boolean silent);
    boolean canPlace(IPlace place);
    ISquare place(IPiece piece, IPlace place, boolean silent);
    ISquare remove(IPiece piece, IPlace place, boolean silent);
    Row getFirstRow();
    Row getLastRow();
    Column getFirstColumn();
    Column getLastColumn();
    int countAdjacent(IColor color);
    IAdjacentSquares getAdjacent(ISquare square);
    boolean isConnected(ISquare origin, ISquare destination);
    void getAllConnections(ISquare origin, List<ISquare> visited);
    boolean hasRoad(IColor color, Line line1, Line line2);
    boolean hasRoad(IColor color);
    @Nullable
    IColor getRoad();
    boolean hasPiecesInEveryColumn(IColor color);
    boolean hasPiecesInEveryRow(IColor color);
    boolean isFull();
    int countFlat(IColor color);
    void reset();
    ISquare[][] toSquareArray();
    double[][][] toDoubleArray();

}
