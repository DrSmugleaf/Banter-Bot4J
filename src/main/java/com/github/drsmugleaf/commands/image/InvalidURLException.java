package com.github.drsmugleaf.commands.image;

/**
 * Created by DrSmugleaf on 10/11/2018
 */
public class InvalidURLException extends RuntimeException {

    public final String URL;

    public InvalidURLException(String url, Throwable cause) {
        super("Invalid image URL: " + url, cause);
        URL = url;
    }

}
