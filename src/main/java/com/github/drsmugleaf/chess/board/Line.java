package com.github.drsmugleaf.chess.board;

import com.github.drsmugleaf.chess.pieces.Color;
import com.github.drsmugleaf.chess.pieces.Piece;
import com.github.drsmugleaf.chess.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Line {

    @NotNull
    private final Square[] SQUARES = new Square[8];

    private Line() {}

    @NotNull
    protected static Line getEmptyLine() {
        Line line = new Line();
        Square[] squares = line.SQUARES;

        for (int i = 0; i < squares.length; i++) {
            squares[i] = new Square();
        }

        return line;
    }

    @NotNull
    protected static Line getMainLine(@NotNull Color color) {
        int row;
        switch (color) {
            case BLACK:
                row = 7;
                break;
            case WHITE:
                row = 1;
                break;
            default:
                throw new IllegalArgumentException("Unrecognized color " + color);
        }

        Line line = new Line();
        Square[] squares = line.SQUARES;

        Piece rook = new Piece(color, Type.ROOK, row, 0);
        squares[0] = new Square(rook);

        Piece knight = new Piece(color, Type.KNIGHT, row, 1);
        squares[1] = new Square(knight);

        Piece bishop = new Piece(color, Type.BISHOP, row, 2);
        squares[2] = new Square(bishop);

        Piece queen = new Piece(color, Type.QUEEN, row, 3);
        squares[3] = new Square(queen);

        Piece king = new Piece(color, Type.KING, row, 4);
        squares[4] = new Square(king);

        Piece bishop2 = new Piece(color, Type.BISHOP, row, 5);
        squares[5] = new Square(bishop2);

        Piece knight2 = new Piece(color, Type.KNIGHT, row, 6);
        squares[6] = new Square(knight2);

        Piece rook2 = new Piece(color, Type.ROOK, row, 7);
        squares[7] = new Square(rook2);

        return line;
    }

    @NotNull
    protected static Line getPawnLine(@NotNull Color color) {
        int row;
        switch (color) {
            case BLACK:
                row = 6;
                break;
            case WHITE:
                row = 1;
                break;
            default:
                throw new IllegalArgumentException("Unrecognized color " + color);
        }

        Line line = new Line();
        Square[] squares = line.SQUARES;

        for (int i = 0; i < squares.length; i++) {
            Piece piece = new Piece(color, Type.PAWN, row, i);
            squares[i] = new Square(piece);
        }

        return line;
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

}
