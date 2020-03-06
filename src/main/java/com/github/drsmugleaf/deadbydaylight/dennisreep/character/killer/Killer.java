package com.github.drsmugleaf.deadbydaylight.dennisreep.character.killer;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.deadbydaylight.dennisreep.API;
import com.github.drsmugleaf.deadbydaylight.dennisreep.character.ICharacter;
import com.github.drsmugleaf.deadbydaylight.dennisreep.character.Tiers;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class Killer implements ICharacter {

    @SerializedName("KillerName")
    public final String NAME;

    @SerializedName("Image")
    public final String IMAGE_URL;

    @SerializedName("Tier")
    public final Tiers TIER;

    @SerializedName("Rating")
    public final double RATING;

    @SerializedName("Ratings")
    public final long RATINGS;

    protected Killer(String name, String imageUrl, Tiers tier, double rating, long ratings) {
        NAME = name;
        IMAGE_URL = imageUrl;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

    public static Killer from(JsonElement json) {
        return API.GSON.fromJson(json, Killer.class);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public InputStream getImage() {
        try {
            return new URL(IMAGE_URL).openStream();
        } catch (IOException e) {
            BanterBot4J.warn("Error getting image from url " + IMAGE_URL);
            return API.getDBDLogo();
        }
    }

    @Nullable
    @Override
    public Double getRating() {
        return RATING;
    }

}
