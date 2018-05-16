package com.github.drsmugleaf.tripwire;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
public abstract class APIException extends RuntimeException {

    APIException() {
        super();
    }

    APIException(String message) {
        super(message);
    }

    APIException(String message, Throwable cause) {
        super(message, cause);
    }

    APIException(Throwable cause) {
        super(cause);
    }

    APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
