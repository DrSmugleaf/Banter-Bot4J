package com.github.drsmugleaf.pokemon.external;

/**
 * Created by DrSmugleaf on 07/09/2018
 */
public class ParsingException extends RuntimeException {

    public ParsingException() {
        super();
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }

    protected ParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
