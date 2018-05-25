package com.github.drsmugleaf.tripwire.session;

import com.github.drsmugleaf.tripwire.TripwireException;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
public class LoginException extends TripwireException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    protected LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
