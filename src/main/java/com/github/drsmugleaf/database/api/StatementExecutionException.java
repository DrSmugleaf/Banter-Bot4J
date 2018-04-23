package com.github.drsmugleaf.database.api;

/**
 * Created by DrSmugleaf on 24/04/2018.
 */
public class StatementExecutionException extends StatementException {

    StatementExecutionException() {
        super();
    }

    StatementExecutionException(String message) {
        super(message);
    }

    StatementExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    StatementExecutionException(Throwable cause) {
        super(cause);
    }

}
