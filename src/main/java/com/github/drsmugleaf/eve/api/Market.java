package com.github.drsmugleaf.eve.api;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Market {

    public static String getCharacterOpenOrders(long characterID) {
        return API.getResponse("characters/" + characterID + "/orders/");
    }

    public static String getCharacterOrderHistory(long characterID) {
        return API.getResponse("characters/" + characterID + "/orders/history/");
    }

    public static String getCorporationOpenOrders(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/orders/");
    }

    public static String getCorporationOrderHistory(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/orders/history/");
    }

    public static String getRegionMarketHistory(long regionID) {
        return API.getResponse("markets/" + regionID + "/history/");
    }

    public static String getRegionMarketOrders(long regionID) {
        return API.getResponse("markets/" + regionID + "/orders/");
    }

    public static String getRegionMarketTypes(long regionID) {
        return API.getResponse("markets/" + regionID + "/types/");
    }

    public static String getMarketGroups() {
        return API.getResponse("markets/groups/");
    }

    public static String getMarketGroupInformation(long marketGroupID) {
        return API.getResponse("markets/groups/" + marketGroupID + "/");
    }

    public static String getMarketPrices() {
        return API.getResponse("markets/prices/");
    }

    public static String getStructureMarketOrders(long structureID) {
        return API.getResponse("markets/structures/" + structureID + "/");
    }

}
