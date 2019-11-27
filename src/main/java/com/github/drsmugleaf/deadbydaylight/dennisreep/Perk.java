package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.Nullable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class Perk implements Comparable<Perk> {

    @SerializedName("PerkName")
    public final String NAME;

    @SerializedName("Tier")
    public final Tiers TIER;

    @SerializedName("Rating")
    public final double RATING;

    @SerializedName("Ratings")
    public final long RATINGS;

    Perk(String name, Tiers tier, double rating, long ratings) {
        NAME = name;
        TIER = tier;
        RATING = rating;
        RATINGS = ratings;
    }

    @Override
    public int compareTo(Perk other) {
        return Double.compare(other.RATING, RATING);
    }

    public String getName() {
        return NAME;
    }

    public Tiers getTier() {
        return TIER;
    }

    public double getRating() {
        return RATING;
    }

    public long getRatings() {
        return RATINGS;
    }

    @Nullable
    public abstract ICharacter getCharacter();

}
