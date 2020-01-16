package com.github.drsmugleaf.tak.board.layout;

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
    int getRow();
    int getColumn();
    List<IPiece> getPieces();
    @Nullable
    IColor getColor();
    @Nullable
    IType getType();
    @Nullable
    IPiece getTopPiece();
    boolean canMove(IType bottomType);
    ISquare move(int amount, ISquare destination, boolean silent);
    boolean canPlace();
    ISquare place(IPiece piece, boolean silent);
    ISquare remove(IPiece piece, boolean silent);
    boolean connectsTo(@Nullable IPiece piece);
    boolean connectsTo(@Nullable ISquare square);
    void onUpdate();
    void reset();
    double[] toDoubleArray(int totalPieces);

}
