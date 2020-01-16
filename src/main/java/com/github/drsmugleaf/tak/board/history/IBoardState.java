package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.tak.pieces.IPiece;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public interface IBoardState {

    int getID();
    IPiece[][][] getPieces();

}
