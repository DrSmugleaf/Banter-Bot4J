package com.github.drsmugleaf.tripwire;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
class LoginException extends APIException {

    LoginException() {
        super();
    }

    LoginException(String message) {
        super(message);
    }

    LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    LoginException(Throwable cause) {
        super(cause);
    }

    LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
