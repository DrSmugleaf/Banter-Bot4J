package com.github.drsmugleaf.tripwire;

/**
 * Created by DrSmugleaf on 24/05/2018.
 */
public class RefreshException extends TripwireException {

    public RefreshException() {
        super();
    }

    public RefreshException(String message) {
        super(message);
    }

    public RefreshException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshException(Throwable cause) {
        super(cause);
    }

    protected RefreshException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
