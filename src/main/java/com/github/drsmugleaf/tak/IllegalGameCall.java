package com.github.drsmugleaf.tak;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public class IllegalGameCall extends GameException {

    public IllegalGameCall(Game game) {
        super(game);
    }

    public IllegalGameCall(Game game, String message) {
        super(game, message);
    }

    public IllegalGameCall(Game game, String message, Throwable cause) {
        super(game, message, cause);
    }

    public IllegalGameCall(Game game, Throwable cause) {
        super(game, cause);
    }

    protected IllegalGameCall(Game game, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(game, message, cause, enableSuppression, writableStackTrace);
    }

}
