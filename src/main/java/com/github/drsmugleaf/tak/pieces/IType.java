package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.board.ISquare;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IType {

    boolean isCapstone();
    boolean isFlatStone();
    boolean isStandingStone();
    String getFileName();
    boolean blocks();
    boolean ignoresBlock();
    boolean formsRoad();
    double toDouble();
    boolean canMoveTo(IPiece topPiece);
    void move(ISquare to, int pieces);

}
