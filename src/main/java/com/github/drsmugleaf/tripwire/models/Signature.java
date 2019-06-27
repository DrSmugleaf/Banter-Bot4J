package com.github.drsmugleaf.tripwire.models;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tripwire.API;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class Signature {

    @SerializedName("id")
    public final int ID;

    @Nullable
    @SerializedName("signatureID")
    public final String SIGNATURE_ID;

    @Nullable
    @SerializedName("systemID")
    public final Integer SYSTEM_ID;

    @Nullable
    @SerializedName("type")
    public final String TYPE;

    @Nullable
    @SerializedName("name")
    public final String NAME;

    @Nullable
    @SerializedName("bookmark")
    public final String BOOKMARK;

    @SerializedName("lifeTime")
    public final Date LIFE_TIME;

    @SerializedName("lifeLeft")
    public final Date LIFE_LEFT;

    @SerializedName("lifeLength")
    public final int LIFE_LENGTH;

    @SerializedName("createdByID")
    public final int CREATED_BY_ID;

    @SerializedName("createdByName")
    public final String CREATED_BY_NAME;

    @SerializedName("modifiedByID")
    public final int MODIFIED_BY_ID;

    @SerializedName("modifiedByName")
    public final String MODIFIED_BY_NAME;

    @SerializedName("modifiedTime")
    public final Date MODIFIED_TIME;

    @SerializedName("maskID")
    public final double MASK_ID;

    public Signature(
            int id,
            @Nullable String signatureID,
            @Nullable Integer systemID,
            @Nullable String type,
            @Nullable String name,
            @Nullable String bookmark,
            Date lifeTime,
            Date lifeLeft,
            int lifeLength,
            int createdByID,
            String createdByName,
            int modifiedByID,
            String modifiedByName,
            Date modifiedTime,
            double maskID
    ) {
        ID = id;
        SIGNATURE_ID = signatureID;
        SYSTEM_ID = systemID;
        TYPE = type;
        NAME = name;
        BOOKMARK = bookmark;
        LIFE_TIME = lifeTime;
        LIFE_LEFT = lifeLeft;
        LIFE_LENGTH = lifeLength;
        CREATED_BY_ID = createdByID;
        CREATED_BY_NAME = createdByName;
        MODIFIED_BY_ID = modifiedByID;
        MODIFIED_BY_NAME = modifiedByName;
        MODIFIED_TIME = modifiedTime;
        MASK_ID = maskID;
    }

    public static Signature fromJson(JsonElement json) {
        return API.GSON.fromJson(json, Signature.class);
    }

    public static Map<Integer, Signature> fromJson(String json) {
        JsonObject signatures = new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("signatures");
        Map<Integer, Signature> signatureList = new HashMap<>();

        for (Map.Entry<String, JsonElement> signatureEntry : signatures.entrySet()) {
            Signature signature = fromJson(signatureEntry.getValue());
            if (signature.SYSTEM_ID == null) {
                continue;
            }

            signatureList.put(signature.ID, signature);
        }

        return signatureList;
    }

}
