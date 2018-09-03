package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 23/04/2018.
 */
public class StatementCreationException extends StatementException {

    StatementCreationException() {
        super();
    }

    StatementCreationException(String message) {
        super(message);
    }

    StatementCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    StatementCreationException(Throwable cause) {
        super(cause);
    }

}
