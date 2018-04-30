package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 24/04/2018.
 */
public class StatementValueException extends StatementException {

    StatementValueException() {
        super();
    }

    StatementValueException(String message) {
        super(message);
    }

    StatementValueException(String message, Throwable cause) {
        super(message, cause);
    }

    StatementValueException(Throwable cause) {
        super(cause);
    }

}
