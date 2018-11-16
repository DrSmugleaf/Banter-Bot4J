package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.IPerk;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class Perk implements Comparable<Perk> {

    @NotNull
    @SerializedName("PerkName")
    public final String NAME;

    @NotNull
    @SerializedName("Tier")
    public final Tiers TIER;

    @SerializedName("Rating")
    public final double RATING;

    @SerializedName("Ratings")
    public final long RATINGS;

    Perk(@NotNull String name, @NotNull Tiers tier, double rating, long ratings) {
        NAME = name;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

    @Override
    public int compareTo(@NotNull Perk other) {
        return Double.compare(other.RATING, RATING);
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public Tiers getTier() {
        return TIER;
    }

    public double getRating() {
        return RATING;
    }

    public long getRatings() {
        return RATINGS;
    }

    @NotNull
    public abstract ICharacter getCharacter();

    @NotNull
    public abstract IPerk toPerk();

}
