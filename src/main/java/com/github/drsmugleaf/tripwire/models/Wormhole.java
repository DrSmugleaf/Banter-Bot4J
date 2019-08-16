package com.github.drsmugleaf.tripwire.models;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tripwire.API;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class Wormhole {

    @SerializedName("id")
    public final int ID;

    @SerializedName("initialID")
    public final int INITIAL_ID;

    @SerializedName("secondaryID")
    public final int SECONDARY_ID;

    @Nullable
    @SerializedName("type")
    public final String TYPE;

    @Nullable
    @SerializedName("parent")
    public final String PARENT;

    @SerializedName("life")
    public final String LIFE;

    @SerializedName("mass")
    public final String MASS;

    @SerializedName("maskID")
    public final double MASK_ID;

    Wormhole(
            int id,
            int initialID,
            int secondaryID,
            @Nullable String type,
            @Nullable String parent,
            String life,
            String mass,
            double maskID
    ) {
        ID = id;
        INITIAL_ID = initialID;
        SECONDARY_ID = secondaryID;
        TYPE = type;
        PARENT = parent;
        LIFE = life;
        MASS = mass;
        MASK_ID = maskID;
    }

    public static Wormhole fromJson(JsonElement json) {
        return API.GSON.fromJson(json, Wormhole.class);
    }

    public static List<Wormhole> fromJson(String json) {
        JsonObject wormholes = new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("wormholes");
        List<Wormhole> wormholeList = new ArrayList<>();

        for (Map.Entry<String, JsonElement> wormholeEntry : wormholes.entrySet()) {
            Wormhole wormhole = fromJson(wormholeEntry.getValue());
            wormholeList.add(wormhole);
        }

        return wormholeList;
    }

}
