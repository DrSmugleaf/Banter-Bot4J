package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.github.drsmugleaf.tak.pieces.IType;

import java.util.List;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface ISquare {

    ISquare copy();
    int getColumn();
    int getRow();
    List<IPiece> getPieces();
    @Nullable
    IColor getColor();
    @Nullable
    IType getType();
    @Nullable
    IPiece getTopPiece();
    boolean canMove(ISquare other, int pieces);
    ISquare move(ISquare destination, int pieces, boolean silent);
    boolean canPlace();
    ISquare place(IPiece piece, boolean silent);
    ISquare remove(IPiece piece, boolean silent);
    boolean connectsTo(@Nullable IPiece piece);
    boolean connectsTo(@Nullable ISquare square);
    void onUpdate();
    void reset();
    double[] toDoubleArray(int totalPieces);

}
