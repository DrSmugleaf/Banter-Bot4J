package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 26/04/2018.
 */
public class ValidationException extends RuntimeException {

    ValidationException() {
        super();
    }

    ValidationException(String message) {
        super(message);
    }

    ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    ValidationException(Throwable cause) {
        super(cause);
    }

}
