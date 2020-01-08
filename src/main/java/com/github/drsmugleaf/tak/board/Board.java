package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.IllegalGameCall;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board {

    private final Preset PRESET;
    private final Line[] COLUMNS;
    private final Line[] ROWS;

    public Board(Preset preset) {
        PRESET = preset;
        int size = PRESET.getSize();

        Line[] rows = new Line[size];
        Line[] columns = new Line[size];

        for (int i = 0; i < size; i++) {
            columns[i] = new Line(size);
            rows[i] = new Line(size);
        }

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                ISquare square = new Square(column, row);
                rows[row].SQUARES[column] = square;
                columns[column].SQUARES[row] = square;
            }
        }

        COLUMNS = columns;
        ROWS = rows;
    }

    public Board(ISquare[][] squares) {
        Preset preset = Preset.getPreset(squares.length);
        if (preset == null) {
            throw new IllegalArgumentException("No preset found for array length " + squares.length);
        }

        PRESET = preset;
        int boardSize = PRESET.getSize();
        for (ISquare[] column : squares) {
            int columnLength = column.length;
            if (columnLength != boardSize) {
                throw new IllegalArgumentException("Array isn't a square. Expected size: " + boardSize + ". Found: " + columnLength);
            }
        }

        Line[] columns = new Line[boardSize];
        Line[] rows = new Line[boardSize];

        for (int i = 0; i < boardSize; i++) {
            columns[i] = new Line(boardSize);
            rows[i] = new Line(boardSize);
        }

        for (int column = 0; column < boardSize; column++) {
            for (int row = 0; row < boardSize; row++) {
                columns[column].setSquare(row, squares[column][row]);
                rows[row].setSquare(column, squares[column][row]);
            }
        }

        COLUMNS = columns;
        ROWS = rows;
    }

    private Board(Board board) {
        this(board.toSquareArray());
    }

    public Board copy() {
        return new Board(this);
    }

    public ISquare[][] toSquareArray() {
        int boardSize = PRESET.getSize();
        ISquare[][] squares = new ISquare[boardSize][boardSize];

        for (int i = 0; i < ROWS.length; i++) {
            ISquare[] row = ROWS[i].getSquares();
            for (int j = 0; j < row.length; j++) {
                squares[i][j] = row[j].copy();
            }
        }

        return squares;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        ISquare[][] board = toSquareArray();

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            ISquare[] row = board[rowIndex];

            for (int column = 0; column < row.length; column++) {
                builder.append(row[column]);

                if (column < row.length - 1) {
                    builder.append(", ");
                } else {
                    builder.append("\n");
                }
            }
        }

        return builder.toString();
    }

    public Board getDefault() {
        return new Board(Preset.getDefault());
    }

    public Line[] getColumns() {
        return COLUMNS;
    }

    public Line[] getRows() {
        return ROWS;
    }

    public Preset getPreset() {
        return PRESET;
    }

    public boolean canMove(ISquare origin, ISquare destination, int pieces) {
        int column = origin.getColumn();
        int row = origin.getRow();
        IAdjacentSquares adjacent = getAdjacent(origin);

        return column < COLUMNS.length &&
               pieces <= PRESET.getCarryLimit() &&
               adjacent.contains(destination) &&
               COLUMNS[column].canMove(row, destination, pieces);
    }

    private ISquare move(ISquare origin, ISquare destination, int pieces, boolean silent) {
        int column = origin.getColumn();
        int row = origin.getRow();
        return COLUMNS[column].move(row, destination, pieces, silent);
    }

    public ISquare move(ISquare origin, ISquare destination, int pieces) {
        return move(origin, destination, pieces, false);
    }

    public ISquare moveSilent(ISquare origin, ISquare destination, int pieces) {
        return move(origin, destination, pieces, true);
    }

    public final ISquare moveSilent(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces) {
        Line[] rows = getRows();
        ISquare origin = rows[originRow].getSquares()[originColumn];
        ISquare destination = rows[destinationRow].getSquares()[destinationColumn];
        return moveSilent(origin, destination, pieces);
    }

    public boolean canPlace(int column, int row) {
        return column < COLUMNS.length && COLUMNS[column].canPlace(row);
    }

    public ISquare place(Piece piece, int column, int row, boolean silent) {
        return COLUMNS[column].place(piece, row, silent);
    }

    public ISquare place(Piece piece, int column, int row) {
        return COLUMNS[column].place(piece, row, false);
    }

    public ISquare placeSilent(Piece piece, int column, int row) {
        return COLUMNS[column].place(piece, row, true);
    }

    protected ISquare remove(Piece piece, int column, int row, boolean silent) {
        return COLUMNS[column].remove(piece, row, silent);
    }

    protected ISquare remove(Piece piece, int column, int row) {
        return COLUMNS[column].remove(piece, row, false);
    }

    protected ISquare removeSilent(Piece piece, int column, int row) {
        return COLUMNS[column].remove(piece, row, true);
    }

    public Line getFirstRow() {
        return ROWS[0];
    }

    public Line getLastRow() {
        return ROWS[ROWS.length - 1];
    }

    public Line getFirstColumn() {
        return COLUMNS[0];
    }

    public Line getLastColumn() {
        return COLUMNS[COLUMNS.length - 1];
    }

    public int countAdjacent(Color color) {
        int amount = 0;

        for (Line column : getColumns()) {
            for (ISquare square : column.getSquares()) {
                if (square.getColor() != color) {
                    continue;
                }

                IAdjacentSquares adjacent = getAdjacent(square);
                amount += adjacent.getConnections().size();
            }
        }

        return amount;
    }

    public IAdjacentSquares getAdjacent(ISquare square) {
        Line[] rows = getRows();
        int rowIndex = square.getRow();
        int columnIndex = square.getColumn();

        if (rowIndex >= rows.length) {
            throw new ArrayIndexOutOfBoundsException("Row " + rowIndex + " is out of bounds");
        }

        ISquare[] row = rows[rowIndex].getSquares();
        if (columnIndex >= row.length) {
            throw new ArrayIndexOutOfBoundsException("Column " + columnIndex + " is out of bounds");
        }

        ISquare centerSquare = row[columnIndex];

        ISquare upSquare = null;
        if (rowIndex > 0) {
            upSquare = ROWS[rowIndex - 1].getSquares()[columnIndex];
        }

        ISquare rightSquare = null;
        if ((columnIndex + 1) < row.length) {
            rightSquare = row[columnIndex + 1];
        }

        ISquare downSquare = null;
        if ((rowIndex + 1) < ROWS.length) {
            downSquare = ROWS[rowIndex + 1].getSquares()[columnIndex];
        }

        ISquare leftSquare = null;
        if (columnIndex > 0) {
            leftSquare = row[columnIndex - 1];
        }

        return new AdjacentSquares(centerSquare, upSquare, rightSquare, downSquare, leftSquare);
    }

    private boolean isConnected(ISquare origin, ISquare destination) {
        if (origin.getColor() == null || origin.getColor() != destination.getColor()) {
            return false;
        }

        ImmutableSet<ISquare> connections = getAdjacent(origin).getConnections();
        List<ISquare> visited = new ArrayList<>();
        visited.add(origin);

        for (ISquare connection : connections) {
            getAllConnections(connection, visited);
        }

        return visited.contains(destination);
    }

    private void getAllConnections(ISquare squareOne, List<ISquare> visited) {
        ImmutableSet<ISquare> connections = getAdjacent(squareOne).getConnections();
        visited.add(squareOne);

        for (ISquare connection : connections) {
            if (visited.contains(connection)) {
                continue;
            }

            getAllConnections(connection, visited);
        }
    }

    public boolean hasRoad(Color color, Line line1, Line line2) {
        for (ISquare origin : line1.getSquares()) {
            if (origin.getColor() != color) {
                continue;
            }

            for (ISquare destination : line2.getSquares()) {
                if (destination.getColor() != color) {
                    continue;
                }

                if (isConnected(origin, destination)) {
                    if (origin.getTopPiece() == null) {
                        throw new IllegalGameCall("Valid road found but the last top piece doesn't exist");
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasRoad(Color color) {
        if (hasPieceInEveryRow(color)) {
            if (hasRoad(color, getFirstRow(), getLastRow())) {
                return true;
            }
        }

        if (hasPieceInEveryColumn(color)) {
            return hasRoad(color, getFirstColumn(), getLastColumn());
        }

        return false;
    }

    @Nullable
    public Color getRoad() {
        for (Color color : Color.values()) {
            if (hasRoad(color)) {
                return color;
            }
        }

        return null;
    }

    public boolean hasPieceInEveryColumn(Color color) {
        for (Line column : COLUMNS) {
            if (!column.hasSquare(color)) {
                return false;
            }
        }

        return true;
    }

    public boolean hasPieceInEveryRow(Color color) {
        for (Line row : ROWS) {
            if (!row.hasSquare(color)) {
                return false;
            }
        }

        return true;
    }

    public boolean isFull() {
        for (ISquare[] row : toSquareArray()) {
            for (ISquare square : row) {
                if (square.getTopPiece() == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public int countFlat(Color color) {
        int amount = 0;

        for (Line column : getColumns()) {
            amount += column.countFlat(color);
        }

        return amount;
    }

    public double[][][] toDoubleArray() {
        Preset preset = getPreset();
        int size = preset.getSize();
        int maximumPieces = 1 + preset.getStones() * 2;
        double[][][] array = new double[size][size][maximumPieces];

        Line[] rows = getRows();
        for (int r = 0; r < rows.length; r++) {
            ISquare[] columns = rows[r].getSquares();
            for (int c = 0; c < columns.length; c++) {
                double[] pieces = columns[c].toDoubleArray(maximumPieces);
                array[r][c] = pieces;
            }
        }

        return array;
    }

    public void reset() {
        int size = getPreset().getSize();

        for (int i = 0; i < size; i++) {
            COLUMNS[i].reset();
            ROWS[i].reset();
        }
    }

}
