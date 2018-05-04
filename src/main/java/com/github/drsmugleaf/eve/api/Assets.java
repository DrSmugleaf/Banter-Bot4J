package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/05/2018.
 */
public class Assets {

    @Nonnull
    public static JsonElement getCharacterAssets(long characterID) {
        return API.getResponse("characters/" + characterID + "/assets/");
    }

    @Nonnull
    public static JsonElement getCharacterAssetLocations(long characterID) {
        return API.getResponse("characters/" + characterID + "/assets/locations/");
    }

    @Nonnull
    public static JsonElement getCharacterAssetNames(long characterID) {
        return API.getResponse("characters/" + characterID + "/assets/names/");
    }

    @Nonnull
    public static JsonElement getCorporationAssets(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/assets/");
    }

    @Nonnull
    public static JsonElement getCorporationAssetLocations(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/assets/locations/");
    }

    @Nonnull
    public static JsonElement getCorporationAssetNames(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/assets/names/");
    }

}
