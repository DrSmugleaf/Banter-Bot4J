package com.github.drsmugleaf.tripwire.route;

import com.github.drsmugleaf.tripwire.TripwireException;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class ESIException extends TripwireException {

    public ESIException() {
        super();
    }

    public ESIException(String message) {
        super(message);
    }

    public ESIException(String message, Throwable cause) {
        super(message, cause);
    }

    public ESIException(Throwable cause) {
        super(cause);
    }

    protected ESIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
