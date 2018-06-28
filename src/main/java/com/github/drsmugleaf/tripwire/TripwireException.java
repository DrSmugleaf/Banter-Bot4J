package com.github.drsmugleaf.tripwire;

/**
 * Created by DrSmugleaf on 24/05/2018.
 */
public abstract class TripwireException extends RuntimeException {

    public TripwireException() {
        super();
    }

    public TripwireException(String message) {
        super(message);
    }

    public TripwireException(String message, Throwable cause) {
        super(message, cause);
    }

    public TripwireException(Throwable cause) {
        super(cause);
    }

    protected TripwireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
