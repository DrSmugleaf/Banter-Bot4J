package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 28/04/2018.
 */
public class ModelInstantiationException extends RuntimeException {

    ModelInstantiationException() {
        super();
    }

    ModelInstantiationException(String message) {
        super(message);
    }

    ModelInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    ModelInstantiationException(Throwable cause) {
        super(cause);
    }

}
