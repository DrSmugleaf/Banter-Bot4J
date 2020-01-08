package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

import java.util.List;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface ISquare {

    ISquare copy();
    int getColumn();
    int getRow();
    List<Piece> getPieces();
    @Nullable
    Color getColor();
    @Nullable
    Type getType();
    @Nullable
    Piece getTopPiece();
    boolean canMove(ISquare other, int pieces);
    ISquare move(ISquare destination, int pieces, boolean silent);
    boolean canPlace();
    ISquare place(Piece piece, boolean silent);
    ISquare remove(Piece piece, boolean silent);
    boolean connectsTo(@Nullable Piece piece);
    boolean connectsTo(@Nullable ISquare square);
    void onUpdate();
    void reset();
    double[] toDoubleArray(int totalPieces);

}
