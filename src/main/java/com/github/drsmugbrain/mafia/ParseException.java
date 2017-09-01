package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public class ParseException extends IllegalArgumentException {

    public ParseException() {
        super();
    }

    public ParseException(@Nonnull String message) {
        super(message);
    }

    public ParseException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }

    public ParseException(@Nonnull Throwable cause) {
        super(cause);
    }

}
