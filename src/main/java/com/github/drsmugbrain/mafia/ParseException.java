package com.github.drsmugbrain.mafia;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public class ParseException extends IllegalArgumentException {

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

}
