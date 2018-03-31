package com.github.drsmugleaf.models.api;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
public class InvalidColumnAnnotationException extends Exception {

    public InvalidColumnAnnotationException() {
        super();
    }

    public InvalidColumnAnnotationException(String message) {
        super(message);
    }

    public InvalidColumnAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidColumnAnnotationException(Throwable cause) {
        super(cause);
    }

}
