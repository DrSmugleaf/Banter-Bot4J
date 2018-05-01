package com.github.drsmugleaf.eve.api;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Market {

    @Nonnull
    public static String getCharacterOpenOrders(long characterID) {
        return API.getResponse("characters/" + characterID + "/orders/");
    }

    @Nonnull
    public static String getCharacterOrderHistory(long characterID) {
        return API.getResponse("characters/" + characterID + "/orders/history/");
    }

    @Nonnull
    public static String getCorporationOpenOrders(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/orders/");
    }

    @Nonnull
    public static String getCorporationOrderHistory(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/orders/history/");
    }

    @Nonnull
    public static String getRegionMarketHistory(long regionID) {
        return API.getResponse("markets/" + regionID + "/history/");
    }

    @Nonnull
    public static String getRegionMarketOrders(long regionID) {
        return API.getResponse("markets/" + regionID + "/orders/");
    }

    @Nonnull
    public static String getRegionMarketTypes(long regionID) {
        return API.getResponse("markets/" + regionID + "/types/");
    }

    @Nonnull
    public static String getMarketGroups() {
        return API.getResponse("markets/groups/");
    }

    @Nonnull
    public static String getMarketGroupInformation(long marketGroupID) {
        return API.getResponse("markets/groups/" + marketGroupID + "/");
    }

    @Nonnull
    public static String getMarketPrices() {
        return API.getResponse("markets/prices/");
    }

    @Nonnull
    public static String getStructureMarketOrders(long structureID) {
        return API.getResponse("markets/structures/" + structureID + "/");
    }

}
