package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
public class InvalidColumnAnnotationException extends Exception {

    protected InvalidColumnAnnotationException() {
        super();
    }

    protected InvalidColumnAnnotationException(String message) {
        super(message);
    }

    protected InvalidColumnAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected InvalidColumnAnnotationException(Throwable cause) {
        super(cause);
    }

}
