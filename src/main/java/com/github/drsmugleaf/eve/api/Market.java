package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Market {

    @Nonnull
    public static JsonElement getCharacterOpenOrders(long characterID) {
        return API.getResponse("characters/" + characterID + "/orders/");
    }

    @Nonnull
    public static JsonElement getCharacterOrderHistory(long characterID) {
        return API.getResponse("characters/" + characterID + "/orders/history/");
    }

    @Nonnull
    public static JsonElement getCorporationOpenOrders(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/orders/");
    }

    @Nonnull
    public static JsonElement getCorporationOrderHistory(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/orders/history/");
    }

    @Nonnull
    public static JsonElement getRegionMarketHistory(long regionID) {
        return API.getResponse("markets/" + regionID + "/history/");
    }

    @Nonnull
    public static JsonElement getRegionMarketOrders(long regionID) {
        return API.getResponse("markets/" + regionID + "/orders/");
    }

    @Nonnull
    public static JsonElement getRegionMarketTypes(long regionID) {
        return API.getResponse("markets/" + regionID + "/types/");
    }

    @Nonnull
    public static JsonElement getMarketGroups() {
        return API.getResponse("markets/groups/");
    }

    @Nonnull
    public static JsonElement getMarketGroupInformation(long marketGroupID) {
        return API.getResponse("markets/groups/" + marketGroupID + "/");
    }

    @Nonnull
    public static JsonElement getMarketPrices() {
        return API.getResponse("markets/prices/");
    }

    @Nonnull
    public static JsonElement getStructureMarketOrders(long structureID) {
        return API.getResponse("markets/structures/" + structureID + "/");
    }

}
