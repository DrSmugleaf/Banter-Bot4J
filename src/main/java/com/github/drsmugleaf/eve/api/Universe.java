package com.github.drsmugleaf.eve.api;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Universe {

    public static String getRegions() {
        return API.getResponse("universe/regions/");
    }

}
