package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.dijkstra.Graph;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board extends Graph<Square> {

    @NotNull
    private final Row[] ROWS;

    @NotNull
    private final Sizes SIZE;

    public Board(@NotNull Sizes size) {
        SIZE = size;

        Row[] rows = new Row[size.getSize()];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Row(size.getSize());
        }

        ROWS = rows;
    }

    @NotNull
    public Square[][] toArray() {
        Square[][] squares = new Square[SIZE.getSize()][SIZE.getSize()];
        for (int i = 0; i < ROWS.length; i++) {
            Row currentRow = ROWS[i];
            squares[i] = currentRow.getSquares();
        }

        return squares;
    }

    @NotNull
    public Board getDefault() {
        return new Board(Sizes.FIVE);
    }

    @NotNull
    public Row[] getRows() {
        return ROWS.clone();
    }

    public boolean canPlace(@NotNull Type type, int row, int square) {
        return row < ROWS.length && ROWS[row].canPlace(type, square);
    }

    public void place(@NotNull Piece piece, int row, int square) {
        ROWS[row].place(piece, square);
    }

    @NotNull
    public Row getFirstRow() {
        return ROWS[0];
    }

    @NotNull
    public Row getLastRow() {
        return ROWS[ROWS.length - 1];
    }

    @NotNull
    public Square[] getFirstColumn() {
        Square[] squares = new Square[SIZE.getSize()];

        for (int i = 0; i < ROWS.length; i++) {
            Row row = ROWS[i];
            squares[i] = row.getSquares()[0];
        }

        return squares;
    }

    @NotNull
    public Square[] getLastColumn() {
        Square[] squares = new Square[SIZE.getSize()];

        for (int i = 0; i < ROWS.length; i++) {
            Row row = ROWS[i];
            Square[] rowSquares = row.getSquares();
            squares[i] = rowSquares[rowSquares.length - 1];
        }

        return squares;
    }

    @NotNull
    public AdjacentSquares getAdjacent(int rowIndex, int columnIndex) {
        if (rowIndex >= ROWS.length) {
            throw new ArrayIndexOutOfBoundsException("Row " + rowIndex + " is out of bounds");
        }

        Square[] row = ROWS[rowIndex].getSquares();
        if (columnIndex >= row.length) {
            throw new ArrayIndexOutOfBoundsException("Column " + columnIndex + " is out of bounds");
        }

        Square centerSquare = row[columnIndex];

        Square upSquare = null;
        if (rowIndex > 0) {
            upSquare = ROWS[rowIndex].getSquares()[columnIndex];
        }

        Square rightSquare = null;
        if ((columnIndex + 1) < row.length) {
            rightSquare = row[columnIndex + 1];
        }

        Square downSquare = null;
        if ((rowIndex + 1) < ROWS.length) {
            downSquare = ROWS[rowIndex].getSquares()[columnIndex];
        }

        Square leftSquare = null;
        if (columnIndex > 0) {
            leftSquare = row[columnIndex - 1];
        }

        return new AdjacentSquares(centerSquare, upSquare, rightSquare, downSquare, leftSquare);
    }

    @NotNull
    @Override
    public Set<Square> getNodes() {
        Set<Square> nodes = new HashSet<>();

        Square[][] board = toArray();
        for (int i = 0; i < board.length; i++) {
            Square[] row = board[i];

            for (int j = 0; j < row.length; j++) {
                Square square = row[j];
                List<Square> adjacentSquares = getAdjacent(i, j).getAll();

                for (Square adjacentSquare : adjacentSquares) {
                    if (square.connectsTo(adjacentSquare)) {
                        square.addDestination(square, 1);
                        adjacentSquare.addDestination(square, 1);
                    }
                }
            }
        }

        return nodes;
    }

    @Nullable
    public Color hasRoad() {
        Row firstRow = getFirstRow();
        for (Square origin : firstRow.getSquares()) {
            calculateShortestPathFromSource(origin);

            for (Square lastSquare : getLastRow().getSquares()) {
                if (!lastSquare.SHORTEST_PATH.isEmpty()) {
                    if (lastSquare.getTopPiece() == null) {
                        throw new IllegalStateException("Valid road found but the last top piece doesn't exist");
                    }

                    return lastSquare.getTopPiece().getColor();
                }
            }
        }

        Square[] firstColumn = getFirstColumn();
        for (Square origin : firstColumn) {
            calculateShortestPathFromSource(origin);

            for (Square lastSquare : getLastColumn()) {
                if (!lastSquare.SHORTEST_PATH.isEmpty()) {
                    if (lastSquare.getTopPiece() == null) {
                        throw new IllegalStateException("Valid road found but the last top piece doesn't exist");
                    }

                    return lastSquare.getTopPiece().getColor();
                }
            }
        }

        return null;
    }

}
