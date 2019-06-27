package com.github.drsmugleaf.pokemon.moves;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public class IllegalZMoveException extends IllegalArgumentException {

    public IllegalZMoveException(BaseMove original, BaseMove zMove) {
        super("Move " + original.NAME + " can't become Z-Move " + zMove.NAME);
    }

}
