package com.github.drsmugleaf.commands.api.registry;

/**
 * Created by DrSmugleaf on 30/08/2018
 */
public class DuplicateCommandNameException extends RuntimeException {

    DuplicateCommandNameException() {
        super();
    }

    DuplicateCommandNameException(String message) {
        super(message);
    }

    DuplicateCommandNameException(String message, Throwable cause) {
        super(message, cause);
    }

    DuplicateCommandNameException(Throwable cause) {
        super(cause);
    }

    DuplicateCommandNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
