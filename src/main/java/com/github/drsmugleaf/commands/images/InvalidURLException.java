package com.github.drsmugleaf.commands.images;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by DrSmugleaf on 10/11/2018
 */
public class InvalidURLException extends IOException {

    @Nonnull
    public final String URL;

    public InvalidURLException(@Nonnull String url, Throwable cause) {
        super("Invalid image URL: " + url, cause);
        URL = url;
    }

}
