package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board {

    @NotNull
    private final Preset PRESET;

    @NotNull
    private final Line[] COLUMNS;

    @NotNull
    private final Line[] ROWS;

    public Board(@NotNull Preset preset) {
        PRESET = preset;
        int boardSize = PRESET.getSize();

        Line[] rows = new Line[boardSize];
        Line[] columns = new Line[boardSize];

        for (int i = 0; i < boardSize; i++) {
            columns[i] = new Line(boardSize);
            rows[i] = new Line(boardSize);
        }

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Square square = new Square(column, row);
                rows[row].SQUARES[column] = square;
                columns[column].SQUARES[row] = square;
            }
        }

        COLUMNS = columns;
        ROWS = rows;
    }

    public Board(@NotNull Square[][] squares) {
        Preset preset = Preset.getPreset(squares.length);
        if (preset == null) {
            throw new IllegalArgumentException("No preset found for array length " + squares.length);
        }

        PRESET = preset;
        int boardSize = PRESET.getSize();
        for (Square[] row : squares) {
            int rowLength = row.length;
            if (rowLength != boardSize) {
                throw new IllegalArgumentException("Array isn't a square. Expected preset " + boardSize + " found " + rowLength);
            }
        }

        Line[] rows = new Line[boardSize];
        Line[] columns = new Line[boardSize];

        for (int i = 0; i < boardSize; i++) {
            columns[i] = new Line(boardSize);
            rows[i] = new Line(boardSize);
        }

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                rows[row].setSquare(column, squares[row][column]);
                columns[column] = new Line(boardSize);
                columns[column].setSquare(row, squares[row][column]);
            }
        }

        COLUMNS = columns;
        ROWS = rows;
    }

    private Board(@NotNull Board board) {
        this(board.toArray());
    }

    @NotNull
    public Board copy() {
        return new Board(this);
    }

    @NotNull
    public Square[][] toArray() {
        int boardSize = PRESET.getSize();
        Square[][] squares = new Square[boardSize][boardSize];

        for (int i = 0; i < ROWS.length; i++) {
            Square[] row = ROWS[i].getSquares();
            for (int j = 0; j < row.length; j++) {
                squares[i][j] = row[j].copy();
            }
        }

        return squares;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Square[][] board = toArray();

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            Square[] row = board[rowIndex];

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

    @NotNull
    public Board getDefault() {
        return new Board(Preset.getDefault());
    }

    @NotNull
    public Line[] getColumns() {
        return COLUMNS;
    }

    @NotNull
    public Line[] getRows() {
        return ROWS;
    }

    @NotNull
    public Preset getPreset() {
        return PRESET;
    }

    public boolean canMove(@NotNull Square origin, @NotNull Square destination, int pieces) {
        int column = origin.getColumn();
        int row = origin.getRow();
        AdjacentSquares adjacent = getAdjacent(origin);

        return column < COLUMNS.length &&
               pieces <= PRESET.getCarryLimit() &&
               adjacent.contains(destination) &&
               COLUMNS[column].canMove(row, destination, pieces);
    }

    @NotNull
    public Square move(@NotNull Square origin, @NotNull Square destination, int pieces) {
        int column = origin.getColumn();
        int row = origin.getRow();
        return COLUMNS[column].move(row, destination, pieces);
    }

    public boolean canPlace(int column, int row) {
        return column < COLUMNS.length && COLUMNS[column].canPlace(row);
    }

    @NotNull
    public Square place(@NotNull Piece piece, int column, int row) {
        return COLUMNS[column].place(piece, row);
    }

    @NotNull
    private Square remove(@NotNull Piece piece, int column, int row) {
        return COLUMNS[column].remove(piece, row);
    }

    @NotNull
    public Line getFirstRow() {
        return ROWS[0];
    }

    @NotNull
    public Line getLastRow() {
        return ROWS[ROWS.length - 1];
    }

    @NotNull
    public Line getFirstColumn() {
        return COLUMNS[0];
    }

    @NotNull
    public Line getLastColumn() {
        return COLUMNS[COLUMNS.length - 1];
    }

    public int countAdjacent(@NotNull Color color) {
        int amount = 0;

        for (Line column : getColumns()) {
            for (Square square : column.getSquares()) {
                if (square.getColor() != color) {
                    continue;
                }

                AdjacentSquares adjacent = getAdjacent(square);
                amount += adjacent.getConnections().size();
            }
        }

        return amount;
    }

    @NotNull
    public AdjacentSquares getAdjacent(@NotNull Square square) {
        Line[] rows = getRows();
        int rowIndex = square.getRow();
        int columnIndex = square.getColumn();

        if (rowIndex >= rows.length) {
            throw new ArrayIndexOutOfBoundsException("Row " + rowIndex + " is out of bounds");
        }

        Square[] row = rows[rowIndex].getSquares();
        if (columnIndex >= row.length) {
            throw new ArrayIndexOutOfBoundsException("Column " + columnIndex + " is out of bounds");
        }

        Square centerSquare = row[columnIndex];

        Square upSquare = null;
        if (rowIndex > 0) {
            upSquare = ROWS[rowIndex - 1].getSquares()[columnIndex];
        }

        Square rightSquare = null;
        if ((columnIndex + 1) < row.length) {
            rightSquare = row[columnIndex + 1];
        }

        Square downSquare = null;
        if ((rowIndex + 1) < ROWS.length) {
            downSquare = ROWS[rowIndex + 1].getSquares()[columnIndex];
        }

        Square leftSquare = null;
        if (columnIndex > 0) {
            leftSquare = row[columnIndex - 1];
        }

        return new AdjacentSquares(centerSquare, upSquare, rightSquare, downSquare, leftSquare);
    }

    private boolean isConnected(@NotNull Square origin, @NotNull Square destination) {
        if (origin.getColor() == null || origin.getColor() != destination.getColor()) {
            return false;
        }

        List<Square> connections = getAdjacent(origin).getConnections();
        List<Square> visited = new ArrayList<>();
        visited.add(origin);

        for (Square connection : connections) {
            getAllConnections(connection, visited);
        }

        return visited.contains(destination);
    }

    private void getAllConnections(@NotNull Square squareOne, @NotNull List<Square> visited) {
        List<Square> connections = getAdjacent(squareOne).getConnections();
        visited.add(squareOne);

        for (Square connection : connections) {
            if (visited.contains(connection)) {
                continue;
            }

            getAllConnections(connection, visited);
        }
    }

    public boolean hasRoad(@NotNull Color color, @NotNull Line line1, @NotNull Line line2) {
        for (Square origin : line1.getSquares()) {
            if (origin.getColor() != color) {
                continue;
            }

            for (Square destination : line2.getSquares()) {
                if (destination.getColor() != color) {
                    continue;
                }

                if (isConnected(origin, destination)) {
                    if (origin.getTopPiece() == null) {
                        throw new IllegalStateException("Valid road found but the last top piece doesn't exist");
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasRoad(@NotNull Color color) {
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
    public Color hasRoad() {
        if (hasRoad(Color.BLACK)) {
            return Color.BLACK;
        } else if (hasRoad(Color.WHITE)) {
            return Color.WHITE;
        } else {
            return null;
        }
    }

    public boolean hasPieceInEveryColumn(@NotNull Color color) {
        for (Line column : COLUMNS) {
            if (!column.hasSquare(color)) {
                return false;
            }
        }

        return true;
    }

    public boolean hasPieceInEveryRow(@NotNull Color color) {
        for (Line row : ROWS) {
            if (!row.hasSquare(color)) {
                return false;
            }
        }

        return true;
    }

    public int with(@NotNull Piece piece, int column, int row, @NotNull Function<Board, Integer> function) {
        place(piece, column, row);
        Integer result = function.apply(this);
        remove(piece, column, row);

        return result;
    }

    public boolean isFull() {
        for (Square[] row : toArray()) {
            for (Square square : row) {
                if (square.getTopPiece() == null) {
                    return false;
                }
            }
        }

        return true;
    }

    public int countFlat(@NotNull Color color) {
        int amount = 0;

        for (Line column : getColumns()) {
            amount += column.countFlat(color);
        }

        return amount;
    }

}
