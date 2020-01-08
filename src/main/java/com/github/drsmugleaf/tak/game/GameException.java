package com.github.drsmugleaf.tak.game;

import com.github.drsmugleaf.tak.TakException;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public abstract class GameException extends TakException {

    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Throwable cause) {
        super(cause);
    }

    protected GameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
