package com.github.drsmugleaf.commands.api;

/**
 * Created by DrSmugleaf on 30/08/2018
 */
public class DuplicateCommandNameException extends RuntimeException {

    public DuplicateCommandNameException() {
        super();
    }

    public DuplicateCommandNameException(String message) {
        super(message);
    }

    public DuplicateCommandNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCommandNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicateCommandNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
