package com.github.drsmugleaf.tak.pieces;

import java.awt.*;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IPiece {

    IPiece copy();
    IColor getColor();
    IType getType();
    void flatten();
    Image toImage(int height, int width);
    double toDouble();

}
