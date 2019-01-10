package com.github.drsmugleaf.chess.pieces;

import com.github.drsmugleaf.chess.board.Coordinates;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public enum Type {

    BISHOP,
    KING,
    KNIGHT,
    PAWN {
        @NotNull
        @Override
        public List<Coordinates> getAvailableMoves(@NotNull Piece piece) {
            Color color = piece.getColor();
            boolean doubleMove;

            switch (color) {
                case BLACK:
                    doubleMove = piece.getRow() == 6;
                    break;
                case WHITE:
                    doubleMove = piece.getRow() == 1;
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized color " + color);
            }

            List<Coordinates> moves = new ArrayList<>();
            moves.add(new Coordinates(piece.getRow() + piece.getForward(), piece.getColumn()));

            if (doubleMove) {
                moves.add(new Coordinates(piece.getRow() + piece.getForward() * 2, piece.getColumn()));
            }

            return moves;
        }
    },
    QUEEN,
    ROOK;

    @NotNull
    public List<Coordinates> getAvailableMoves(@NotNull Piece piece) {
        return null;
    }

}
