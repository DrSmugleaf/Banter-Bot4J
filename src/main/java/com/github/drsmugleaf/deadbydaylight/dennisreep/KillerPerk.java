package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Tiers;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillerPerk extends Perk {

    @Nonnull
    @SerializedName("Killer")
    public final String KILLER;

    KillerPerk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull String killer, @Nonnull Tiers tier, double rating, long ratings) {
        super(imageUrl, name, tier, rating, ratings);
        KILLER = killer;
    }

}
