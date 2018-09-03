package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
public class InvalidColumnException extends RuntimeException {

    public InvalidColumnException() {
        super();
    }

    public InvalidColumnException(String message) {
        super(message);
    }

    public InvalidColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidColumnException(Throwable cause) {
        super(cause);
    }

}
