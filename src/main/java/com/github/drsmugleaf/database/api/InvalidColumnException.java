package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
public class InvalidColumnException extends RuntimeException {

    InvalidColumnException() {
        super();
    }

    InvalidColumnException(String message) {
        super(message);
    }

    InvalidColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    InvalidColumnException(Throwable cause) {
        super(cause);
    }

}
