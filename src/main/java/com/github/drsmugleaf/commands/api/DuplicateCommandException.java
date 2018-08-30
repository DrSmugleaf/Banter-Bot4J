package com.github.drsmugleaf.commands.api;

/**
 * Created by DrSmugleaf on 30/08/2018
 */
public class DuplicateCommandException extends RuntimeException {

    public DuplicateCommandException() {
        super();
    }

    public DuplicateCommandException(String message) {
        super(message);
    }

    public DuplicateCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCommandException(Throwable cause) {
        super(cause);
    }

    protected DuplicateCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
