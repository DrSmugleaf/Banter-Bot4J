package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 03/05/2018.
 */
public class Alliance {

    @Nonnull
    public static JsonElement getAlliances() {
        return API.getResponse("alliances/");
    }

    @Nonnull
    public static JsonElement getAllianceNames() {
        return API.getResponse("alliances/names");
    }

    @Nonnull
    public static JsonElement getAllianceInformation(long allianceID) {
        return API.getResponse("alliances/" + allianceID + "/");
    }

    @Nonnull
    public static JsonElement getAllianceCorporations(long allianceID) {
        return API.getResponse("alliances/" + allianceID + "/corporations/");
    }

    @Nonnull
    public static JsonElement getAllianceIcon(long allianceID) {
        return API.getResponse("alliances/" + allianceID + "/icons/");
    }

}
