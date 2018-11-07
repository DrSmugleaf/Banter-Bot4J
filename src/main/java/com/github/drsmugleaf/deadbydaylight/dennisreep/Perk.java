package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.IPerk;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class Perk implements Comparable<Perk> {

    @Nonnull
    @SerializedName(value = "Image", alternate = {"PerkIcon"})
    public final String IMAGE_URL;

    @Nonnull
    @SerializedName("PerkName")
    public final String NAME;

    @Nonnull
    @SerializedName("Tier")
    public final Tiers TIER;

    @Nonnull
    @SerializedName("Rating")
    public final double RATING;

    @Nonnull
    @SerializedName("Ratings")
    public final long RATINGS;

    Perk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull Tiers tier, double rating, long ratings) {
        IMAGE_URL = imageUrl;
        NAME = name;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

    @Override
    public int compareTo(@NotNull Perk other) {
        return Double.compare(other.RATING, RATING);
    }

    @Nonnull
    public abstract IPerk toPerk();

}
