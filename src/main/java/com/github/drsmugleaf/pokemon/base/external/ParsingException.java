package com.github.drsmugleaf.pokemon.base.external;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class ParsingException extends IllegalArgumentException {

    public ParsingException() {
        super();
    }

    public ParsingException(String s) {
        super(s);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(Throwable cause) {
        super(cause);
    }

}
