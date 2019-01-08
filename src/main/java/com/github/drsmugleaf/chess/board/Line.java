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
        Line line = new Line();
        Square[] squares = line.SQUARES;

        Piece rook = new Piece(color, Type.ROOK);
        squares[0] = new Square(rook);

        Piece knight = new Piece(color, Type.KNIGHT);
        squares[1] = new Square(knight);

        Piece bishop = new Piece(color, Type.BISHOP);
        squares[2] = new Square(bishop);

        Piece queen = new Piece(color, Type.QUEEN);
        squares[3] = new Square(queen);

        Piece king = new Piece(color, Type.KING);
        squares[4] = new Square(king);

        Piece bishop2 = new Piece(color, Type.BISHOP);
        squares[5] = new Square(bishop2);

        Piece knight2 = new Piece(color, Type.KNIGHT);
        squares[6] = new Square(knight2);

        Piece rook2 = new Piece(color, Type.ROOK);
        squares[7] = new Square(rook2);

        return line;
    }

    @NotNull
    protected static Line getPawnLine(@NotNull Color color) {
        Line line = new Line();
        Square[] squares = line.SQUARES;

        for (int i = 0; i < squares.length; i++) {
            Piece piece = new Piece(color, Type.PAWN);
            squares[i] = new Square(piece);
        }

        return line;
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

}
