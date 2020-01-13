package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.game.IllegalGameCall;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board implements IBoard {

    private final IPreset PRESET;
    private final Row[] ROWS;
    private final Column[] COLUMNS;

    public Board(IPreset preset) {
        PRESET = preset;
        int size = PRESET.getSize();

        Row[] rows = new Row[size];
        Column[] columns = new Column[size];

        for (int i = 0; i < size; i++) {
            rows[i] = new Row(size);
            columns[i] = new Column(size);
        }

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                ISquare square = new Square(row, column);
                rows[row].getSquares()[column] = square;
                columns[column].getSquares()[row] = square;
            }
        }

        ROWS = rows;
        COLUMNS = columns;
    }

    public Board(ISquare[][] squares) {
        IPreset preset = Preset.getPreset(squares.length);
        if (preset == null) {
            throw new IllegalArgumentException("No preset found for array length " + squares.length);
        }

        PRESET = preset;
        int boardSize = PRESET.getSize();
        for (ISquare[] row : squares) {
            int rowLength = row.length;
            if (rowLength != boardSize) {
                throw new IllegalArgumentException("Array isn't a square. Expected size: " + boardSize + ". Found: " + rowLength);
            }
        }

        Row[] rows = new Row[boardSize];
        Column[] columns = new Column[boardSize];

        for (int i = 0; i < boardSize; i++) {
            columns[i] = new Column(boardSize);
            rows[i] = new Row(boardSize);
        }

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                rows[row].setSquare(column, squares[row][column]);
                columns[column].setSquare(row, squares[row][column]);
            }
        }

        ROWS = rows;
        COLUMNS = columns;
    }

    private Board(IBoard board) {
        this(board.toSquareArray());
    }

    @Override
    public IBoard copy() {
        return new Board(this);
    }

    @Override
    public IBoard getDefault() {
        return new Board(Preset.getDefault());
    }

    @Override
    public Column[] getColumns() {
        return COLUMNS;
    }

    @Override
    public Row[] getRows() {
        return ROWS;
    }

    @Override
    public IPreset getPreset() {
        return PRESET;
    }

    @Override
    public boolean canMove(ISquare origin, ISquare destination, int pieces) {
        int originRow = origin.getRow();
        int originColumn = origin.getColumn();
        IAdjacentSquares adjacent = getAdjacent(origin);

        return originRow < getRows().length &&
               pieces <= PRESET.getCarryLimit() &&
               adjacent.contains(destination) &&
               getRows()[originRow].canMove(originColumn, destination, pieces);
    }

    @Override
    public ISquare move(ISquare origin, ISquare destination, int pieces, boolean silent) {
        int originRow = origin.getRow();
        int originColumn = origin.getColumn();
        return getRows()[originRow].move(originColumn, destination, pieces, silent);
    }

    @Override
    public ISquare move(ISquare origin, ISquare destination, int pieces) {
        return move(origin, destination, pieces, false);
    }

    @Override
    public ISquare moveSilent(ISquare origin, ISquare destination, int pieces) {
        return move(origin, destination, pieces, true);
    }

    @Override
    public final ISquare moveSilent(int originRow, int originColumn, int destinationRow, int destinationColumn, int pieces) {
        Row[] rows = getRows();
        ISquare origin = rows[originRow].getSquares()[originColumn];
        ISquare destination = rows[destinationRow].getSquares()[destinationColumn];
        return moveSilent(origin, destination, pieces);
    }

    @Override
    public boolean canPlace(int row, int column) {
        return column < COLUMNS.length && COLUMNS[column].canPlace(row);
    }

    @Override
    public ISquare place(IPiece piece, int row, int column, boolean silent) {
        return COLUMNS[column].place(piece, row, silent);
    }

    @Override
    public ISquare place(IPiece piece, int row, int column) {
        return COLUMNS[column].place(piece, row, false);
    }

    @Override
    public ISquare placeSilent(IPiece piece, int row, int column) {
        return COLUMNS[column].place(piece, row, true);
    }

    @Override
    public ISquare remove(IPiece piece, int row, int column, boolean silent) {
        return COLUMNS[column].remove(piece, row, silent);
    }

    @Override
    public ISquare remove(IPiece piece, int row, int column) {
        return COLUMNS[column].remove(piece, row, false);
    }

    @Override
    public ISquare removeSilent(IPiece piece, int row, int column) {
        return COLUMNS[column].remove(piece, row, true);
    }

    @Override
    public Row getFirstRow() {
        return getRows()[0];
    }

    @Override
    public Row getLastRow() {
        return getRows()[getRows().length - 1];
    }

    @Override
    public Column getFirstColumn() {
        return COLUMNS[0];
    }

    @Override
    public Column getLastColumn() {
        return COLUMNS[COLUMNS.length - 1];
    }

    @Override
    public int countAdjacent(IColor color) {
        int amount = 0;

        for (Row row : getRows()) {
            for (ISquare square : row.getSquares()) {
                if (square.getColor() != color) {
                    continue;
                }

                IAdjacentSquares adjacent = getAdjacent(square);
                amount += adjacent.getConnections().size();
            }
        }

        return amount;
    }

    @Override
    public IAdjacentSquares getAdjacent(ISquare square) {
        Row[] rows = getRows();
        int rowIndex = square.getRow();
        int columnIndex = square.getColumn();

        if (rowIndex >= rows.length) {
            throw new ArrayIndexOutOfBoundsException("Row " + rowIndex + " is out of bounds");
        }

        ISquare[] row = rows[rowIndex].getSquares();
        if (columnIndex >= row.length) {
            throw new ArrayIndexOutOfBoundsException("Column " + columnIndex + " is out of bounds");
        }

        ISquare center = row[columnIndex];

        ISquare up = null;
        if (rowIndex > 0) {
            up = getRows()[rowIndex - 1].getSquares()[columnIndex];
        }

        ISquare right = null;
        if ((columnIndex + 1) < row.length) {
            right = row[columnIndex + 1];
        }

        ISquare down = null;
        if ((rowIndex + 1) < getRows().length) {
            down = getRows()[rowIndex + 1].getSquares()[columnIndex];
        }

        ISquare left = null;
        if (columnIndex > 0) {
            left = row[columnIndex - 1];
        }

        return new AdjacentSquares(center, up, right, down, left);
    }

    @Override
    public boolean isConnected(ISquare origin, ISquare destination) {
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

    @Override
    public void getAllConnections(ISquare origin, List<ISquare> visited) {
        ImmutableSet<ISquare> connections = getAdjacent(origin).getConnections();
        visited.add(origin);

        for (ISquare connection : connections) {
            if (visited.contains(connection)) {
                continue;
            }

            getAllConnections(connection, visited);
        }
    }

    @Override
    public boolean hasRoad(IColor color, Line line1, Line line2) {
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

    @Override
    public boolean hasRoad(IColor color) {
        if (hasPiecesInEveryRow(color)) {
            if (hasRoad(color, getFirstRow(), getLastRow())) {
                return true;
            }
        }

        if (hasPiecesInEveryColumn(color)) {
            return hasRoad(color, getFirstColumn(), getLastColumn());
        }

        return false;
    }

    @Nullable
    @Override
    public IColor getRoad() {
        for (IColor color : Color.values()) {
            if (hasRoad(color)) {
                return color;
            }
        }

        return null;
    }

    @Override
    public boolean hasPiecesInEveryColumn(IColor color) {
        for (Column column : COLUMNS) {
            if (!column.hasSquare(color)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hasPiecesInEveryRow(IColor color) {
        for (Row row : getRows()) {
            if (!row.hasSquare(color)) {
                return false;
            }
        }

        return true;
    }

    @Override
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

    @Override
    public int countFlat(IColor color) {
        int amount = 0;

        for (Column column : getColumns()) {
            amount += column.countFlat(color);
        }

        return amount;
    }

    @Override
    public void reset() {
        int size = getPreset().getSize();

        for (int i = 0; i < size; i++) {
            getRows()[i].reset();
            getColumns()[i].reset();
        }
    }

    @Override
    public double[][][] toDoubleArray() {
        IPreset preset = getPreset();
        int size = preset.getSize();
        int maximumPieces = 1 + preset.getStones() * 2;
        double[][][] array = new double[size][size][maximumPieces];

        Row[] rows = getRows();
        for (int r = 0; r < rows.length; r++) {
            ISquare[] columns = rows[r].getSquares();
            for (int c = 0; c < columns.length; c++) {
                double[] pieces = columns[c].toDoubleArray(maximumPieces);
                array[r][c] = pieces;
            }
        }

        return array;
    }

    @Override
    public ISquare[][] toSquareArray() {
        int boardSize = PRESET.getSize();
        ISquare[][] squares = new ISquare[boardSize][boardSize];

        for (int i = 0; i < getRows().length; i++) {
            ISquare[] row = getRows()[i].getSquares();
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

        for (ISquare[] row : board) {
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

}
