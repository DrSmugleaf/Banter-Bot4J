package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;
import org.json.JSONObject;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Universe {

    @Nonnull
    public static JsonElement getRegions() {
        return API.getResponse("universe/regions/");
    }

}
