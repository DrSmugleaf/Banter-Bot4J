package com.github.drsmugleaf.tripwire;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
public abstract class APIException extends RuntimeException {

    public APIException() {
        super();
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    protected APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
