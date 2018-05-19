package com.github.drsmugleaf.tripwire;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
class Signature {

    @SerializedName("id")
    final int ID;

    @Nullable
    @SerializedName("signatureID")
    final String SIGNATURE_ID;

    @Nullable
    @SerializedName("systemID")
    final Integer SYSTEM_ID;

    @Nullable
    @SerializedName("type")
    final String TYPE;

    @Nullable
    @SerializedName("name")
    final String NAME;

    @Nullable
    @SerializedName("bookmark")
    final String BOOKMARK;

    @Nonnull
    @SerializedName("lifeTime")
    final Date LIFE_TIME;

    @Nonnull
    @SerializedName("lifeLeft")
    final Date LIFE_LEFT;

    @SerializedName("lifeLength")
    final int LIFE_LENGTH;

    @SerializedName("createdByID")
    final int CREATED_BY_ID;

    @Nonnull
    @SerializedName("createdByName")
    final String CREATED_BY_NAME;

    @SerializedName("modifiedByID")
    final int MODIFIED_BY_ID;

    @Nonnull
    @SerializedName("modifiedByName")
    final String MODIFIED_BY_NAME;

    @Nonnull
    @SerializedName("modifiedTime")
    final Date MODIFIED_TIME;

    @SerializedName("maskID")
    final double MASK_ID;

    public Signature(
            int id,
            @Nullable String signatureID,
            @Nullable Integer systemID,
            @Nullable String type,
            @Nullable String name,
            @Nullable String bookmark,
            @Nonnull Date lifeTime,
            @Nonnull Date lifeLeft,
            int lifeLength,
            int createdByID,
            @Nonnull String createdByName,
            int modifiedByID,
            @Nonnull String modifiedByName,
            @Nonnull Date modifiedTime,
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

    @Nonnull
    static Signature fromJson(@Nonnull JsonElement json) {
        return API.gson.fromJson(json, Signature.class);
    }

    @Nonnull
    static List<Signature> fromJson(@Nonnull String json) {
        JsonObject signatures = new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("signatures");
        List<Signature> signatureList = new ArrayList<>();

        for (Map.Entry<String, JsonElement> signatureEntry : signatures.entrySet()) {
            Signature signature = fromJson(signatureEntry.getValue());
            signatureList.add(signature);
        }

        return signatureList;
    }

}
