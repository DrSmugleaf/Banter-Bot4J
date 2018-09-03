package com.github.drsmugleaf.music;

import java.io.IOException;

/**
 * Created by DrSmugleaf on 05/09/2017.
 */
public class SearchErrorException extends IOException {

    public SearchErrorException(String message) {
        super(message);
    }

    public SearchErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchErrorException(Throwable cause) {
        super(cause);
    }

}
