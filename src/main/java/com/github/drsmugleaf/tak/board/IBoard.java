package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
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
    boolean canMove(ISquare origin, ISquare destination, int pieces);
    ISquare move(ISquare origin, ISquare destination, int pieces, boolean silent);
    ISquare move(ISquare origin, ISquare destination, int pieces);
    ISquare moveSilent(ISquare origin, ISquare destination, int pieces);
    ISquare moveSilent(int originRow, int originColumn, int destinationRow, int destinationColumn, int pieces);
    boolean canPlace(int row, int column);
    ISquare place(IPiece piece, int row, int column, boolean silent);
    ISquare place(IPiece piece, int row, int column);
    ISquare placeSilent(IPiece piece, int row, int column);
    ISquare remove(IPiece piece, int row, int column, boolean silent);
    ISquare remove(IPiece piece, int row, int column);
    ISquare removeSilent(IPiece piece, int row, int column);
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
