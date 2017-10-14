package com.github.drsmugbrain.pokemon.stats;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public class IllegalZMoveException extends Exception {

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

}
