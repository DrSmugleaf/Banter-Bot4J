package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 24/06/2018
 */
public class ConnectionException extends RuntimeException {

    ConnectionException() {
        super();
    }

    ConnectionException(String message) {
        super(message);
    }

    ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    ConnectionException(Throwable cause) {
        super(cause);
    }

    protected ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
