package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Killers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class Killer {

    @Nonnull
    @SerializedName("Image")
    public final String IMAGE_URL;

    @Nonnull
    @SerializedName("KillerName")
    public final Killers KILLER;

    @Nonnull
    @SerializedName("Tier")
    public final Tiers TIER;

    @Nonnull
    @SerializedName("Rating")
    public final double RATING;

    @Nonnull
    @SerializedName("Ratings")
    public final long RATINGS;

    Killer(@Nonnull String imageUrl, @Nonnull Killers killer, @Nonnull Tiers tier, double rating, long ratings) {
        IMAGE_URL = imageUrl;
        KILLER = killer;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

    @Nonnull
    public static Killer from(@Nonnull JsonElement json) {
        JsonObject object = json.getAsJsonObject();
        String killerName = object.get("KillerName").getAsString();
        killerName = NameResolver.resolveKillerName(killerName);
        object.addProperty("KillerName", killerName);

        return API.GSON.fromJson(json, Killer.class);
    }

}
