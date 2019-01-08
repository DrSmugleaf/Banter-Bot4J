package com.github.drsmugleaf.chess.board;

import com.github.drsmugleaf.chess.pieces.Piece;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Square {

    @Nullable
    private Piece piece;

    protected Square() {}

    @Nullable
    public Piece getPiece() {
        return piece;
    }

}
