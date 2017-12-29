package com.github.drsmugleaf;

/**
 * Created by DrSmugleaf on 22/06/2017.
 */
public class DiscordException extends Exception {

    public DiscordException() {
        super();
    }

    public DiscordException(String message) {
        super(message);
    }

    public DiscordException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiscordException(Throwable cause) {
        super(cause);
    }

}
