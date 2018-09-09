package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public class IllegalZMoveException extends IllegalArgumentException {

    public IllegalZMoveException() {
        super();
    }

    public IllegalZMoveException(String message) {
        super(message);
    }

    public IllegalZMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalZMoveException(Throwable cause) {
        super(cause);
    }

    public IllegalZMoveException(@Nonnull BaseMove original, @Nonnull BaseMove zMove) {
        super("Move " + original.NAME + " can't become Z-Move " + zMove.NAME);
    }

}
