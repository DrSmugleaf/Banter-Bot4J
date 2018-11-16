package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Killers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class Killer {

    @NotNull
    @SerializedName("Image")
    public final String IMAGE_URL;

    @NotNull
    @SerializedName("KillerName")
    public final Killers KILLER;

    @NotNull
    @SerializedName("Tier")
    public final Tiers TIER;

    @NotNull
    @SerializedName("Rating")
    public final double RATING;

    @NotNull
    @SerializedName("Ratings")
    public final long RATINGS;

    Killer(@NotNull String imageUrl, @NotNull Killers killer, @NotNull Tiers tier, double rating, long ratings) {
        IMAGE_URL = imageUrl;
        KILLER = killer;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

    @NotNull
    public static Killer from(@NotNull JsonElement json) {
        JsonObject object = json.getAsJsonObject();
        String killerName = object.get("KillerName").getAsString();
        killerName = NameResolver.resolveKillerName(killerName);
        object.addProperty("KillerName", killerName);

        return API.GSON.fromJson(json, Killer.class);
    }

}
