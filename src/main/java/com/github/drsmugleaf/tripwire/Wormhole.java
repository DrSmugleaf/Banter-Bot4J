package com.github.drsmugleaf.tripwire;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

    @Nonnull
    @SerializedName("life")
    public final String LIFE;

    @Nonnull
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
            @Nonnull String life,
            @Nonnull String mass,
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

}
