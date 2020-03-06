package com.github.drsmugleaf.deadbydaylight.dennisreep.character;

import com.github.drsmugleaf.Nullable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class Perk implements Comparable<Perk> {

    @SerializedName("PerkName")
    public String NAME;

    @SerializedName("Tier")
    public Tiers TIER;

    @SerializedName("Rating")
    public double RATING;

    @SerializedName("Ratings")
    public long RATINGS;

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
    public abstract String getCharacterName();

    @Nullable
    public abstract ICharacter getCharacter();

}
