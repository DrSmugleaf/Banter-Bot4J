package com.github.drsmugleaf.tak;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public abstract class GameException extends TakException {

    public GameException(Game game) {
        super();
    }

    public GameException(Game game, String message) {
        super(message);
    }

    public GameException(Game game, String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Game game, Throwable cause) {
        super(cause);
    }

    protected GameException(Game game, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
