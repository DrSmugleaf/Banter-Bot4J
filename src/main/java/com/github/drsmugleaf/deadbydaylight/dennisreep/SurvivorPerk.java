package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Tiers;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class SurvivorPerk extends Perk {

    @Nonnull
    @SerializedName("Survivor")
    public final String SURVIVOR;

    SurvivorPerk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull Tiers tier, double rating, long ratings, @Nonnull String survivor) {
        super(imageUrl, name, tier, rating, ratings);
        SURVIVOR = survivor;
    }

}
