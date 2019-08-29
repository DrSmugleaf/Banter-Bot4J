package com.github.drsmugleaf.tak;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public class IllegalGameCall extends GameException {

    public IllegalGameCall() {
        super();
    }

    public IllegalGameCall(String message) {
        super(message);
    }

    public IllegalGameCall(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalGameCall(Throwable cause) {
        super(cause);
    }

    protected IllegalGameCall(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
