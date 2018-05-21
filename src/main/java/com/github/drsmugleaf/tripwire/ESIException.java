package com.github.drsmugleaf.tripwire;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class ESIException extends APIException {

    ESIException() {
        super();
    }

    ESIException(String message) {
        super(message);
    }

    ESIException(String message, Throwable cause) {
        super(message, cause);
    }

    ESIException(Throwable cause) {
        super(cause);
    }

    ESIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
