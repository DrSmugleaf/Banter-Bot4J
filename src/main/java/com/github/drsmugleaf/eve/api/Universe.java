package com.github.drsmugleaf.eve.api;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Universe {

    @Nonnull
    public static String getRegions() {
        return API.getResponse("universe/regions/");
    }

}
