package com.github.drsmugleaf.env;

/**
 * Created by DrSmugleaf on 30/04/2018.
 */
class InitializationException extends RuntimeException {

    InitializationException() {
        super();
    }

    InitializationException(String message) {
        super(message);
    }

    InitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    InitializationException(Throwable cause) {
        super(cause);
    }

}
