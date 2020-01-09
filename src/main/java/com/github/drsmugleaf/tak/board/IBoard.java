package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;

import java.util.List;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IBoard {

    IBoard copy();
    IBoard getDefault();
    Line[] getColumns();
    Line[] getRows();
    IPreset getPreset();
    boolean canMove(ISquare origin, ISquare destination, int pieces);
    ISquare move(ISquare origin, ISquare destination, int pieces, boolean silent);
    ISquare move(ISquare origin, ISquare destination, int pieces);
    ISquare moveSilent(ISquare origin, ISquare destination, int pieces);
    ISquare moveSilent(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces);
    boolean canPlace(int column, int row);
    ISquare place(Piece piece, int column, int row, boolean silent);
    ISquare place(Piece piece, int column, int row);
    ISquare placeSilent(Piece piece, int column, int row);
    ISquare remove(Piece piece, int column, int row, boolean silent);
    ISquare remove(Piece piece, int column, int row);
    ISquare removeSilent(Piece piece, int column, int row);
    Line getFirstRow();
    Line getLastRow();
    Line getFirstColumn();
    Line getLastColumn();
    int countAdjacent(Color color);
    IAdjacentSquares getAdjacent(ISquare square);
    boolean isConnected(ISquare origin, ISquare destination);
    void getAllConnections(ISquare origin, List<ISquare> visited);
    boolean hasRoad(Color color, Line line1, Line line2);
    boolean hasRoad(Color color);
    @Nullable
    Color getRoad();
    boolean hasPiecesInEveryColumn(Color color);
    boolean hasPiecesInEveryRow(Color color);
    boolean isFull();
    int countFlat(Color color);
    void reset();
    ISquare[][] toSquareArray();
    double[][][] toDoubleArray();

}
