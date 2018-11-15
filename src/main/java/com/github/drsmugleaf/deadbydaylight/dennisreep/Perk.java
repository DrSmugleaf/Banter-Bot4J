package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.IPerk;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class Perk implements Comparable<Perk> {

    @Nonnull
    @SerializedName("PerkName")
    public final String NAME;

    @Nonnull
    @SerializedName("Tier")
    public final Tiers TIER;

    @SerializedName("Rating")
    public final double RATING;

    @SerializedName("Ratings")
    public final long RATINGS;

    Perk(@Nonnull String name, @Nonnull Tiers tier, double rating, long ratings) {
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
    public String getName() {
        return NAME;
    }

    @Nonnull
    public Tiers getTier() {
        return TIER;
    }

    public double getRating() {
        return RATING;
    }

    public long getRatings() {
        return RATINGS;
    }

    @Nonnull
    public abstract ICharacter getCharacter();

    @Nonnull
    public abstract IPerk toPerk();

}
