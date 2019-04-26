package com.github.drsmugleaf.commands.api.converter;

/**
 * Created by DrSmugleaf on 21/04/2019
 */
public class ConversionException extends Exception {

    public ConversionException() {}

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }

    public ConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
