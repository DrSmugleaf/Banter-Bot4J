package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 09/04/2018.
 */
public class ModelException extends RuntimeException {

    public ModelException() {
        super();
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }

}
