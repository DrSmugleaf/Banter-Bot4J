package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
class InvalidColumnException extends Exception {

    protected InvalidColumnException() {
        super();
    }

    protected InvalidColumnException(String message) {
        super(message);
    }

    protected InvalidColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    protected InvalidColumnException(Throwable cause) {
        super(cause);
    }

}
