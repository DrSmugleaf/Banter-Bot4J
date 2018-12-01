package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Piece;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square {

    @NotNull
    private final List<Piece> PIECES = new ArrayList<>();

    Square() {}

    @NotNull
    public List<Piece> getPieces() {
        return new ArrayList<>(PIECES);
    }

}
