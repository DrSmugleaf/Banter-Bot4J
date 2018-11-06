package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Tiers;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillerPerk {

    @Nonnull
    @SerializedName("Image")
    public final String IMAGE_URL;

    @Nonnull
    @SerializedName("PerkName")
    public final String NAME;

    @Nonnull
    @SerializedName("Killer")
    public final String KILLER;

    @Nonnull
    @SerializedName("Tier")
    public final Tiers TIER;

    @Nonnull
    @SerializedName("Rating")
    public final double RATING;

    @Nonnull
    @SerializedName("Ratings")
    public final long RATINGS;

    KillerPerk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull String killer, @Nonnull Tiers tier, double rating, long ratings) {
        IMAGE_URL = imageUrl;
        NAME = name;
        KILLER = killer;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

}
