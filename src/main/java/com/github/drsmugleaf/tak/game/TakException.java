package com.github.drsmugleaf.tak.game;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public abstract class TakException extends RuntimeException {

    public TakException() {
        super();
    }

    public TakException(String message) {
        super(message);
    }

    public TakException(String message, Throwable cause) {
        super(message, cause);
    }

    public TakException(Throwable cause) {
        super(cause);
    }

    protected TakException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
