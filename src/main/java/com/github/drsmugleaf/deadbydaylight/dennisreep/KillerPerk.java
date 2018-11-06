package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillerPerk extends Perk {

    @Nonnull
    @SerializedName(value = "Killer", alternate = {"PerkKiller"})
    public final Killers KILLER;

    KillerPerk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull Tiers tier, double rating, long ratings, @Nonnull Killers killer) {
        super(imageUrl, name, tier, rating, ratings);
        KILLER = killer;
    }

    @Nonnull
    public static KillerPerk from(@Nonnull KillerPerks perk) {
        return PerksAPI.getKillerPerkData().get(perk);
    }

    @Nonnull
    @Override
    public KillerPerks toPerk() {
        return KillerPerks.from(NAME);
    }

}
