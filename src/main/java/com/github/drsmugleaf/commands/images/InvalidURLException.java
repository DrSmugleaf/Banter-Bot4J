package com.github.drsmugleaf.commands.images;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;

/**
 * Created by DrSmugleaf on 10/11/2018
 */
public class InvalidURLException extends IOException {

    @NotNull
    public final String URL;

    public InvalidURLException(@NotNull String url, Throwable cause) {
        super("Invalid image URL: " + url, cause);
        URL = url;
    }

}
