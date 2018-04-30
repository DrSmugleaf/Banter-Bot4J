package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 09/04/2018.
 */
public class ModelException extends RuntimeException {

    ModelException() {
        super();
    }

    ModelException(String message) {
        super(message);
    }

    ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    ModelException(Throwable cause) {
        super(cause);
    }

}
