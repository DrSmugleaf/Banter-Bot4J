package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 24/04/2018.
 */
public abstract class StatementException extends RuntimeException {

    StatementException() {
        super();
    }

    StatementException(String message) {
        super(message);
    }

    StatementException(String message, Throwable cause) {
        super(message, cause);
    }

    StatementException(Throwable cause) {
        super(cause);
    }

}
