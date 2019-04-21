package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillerPerk extends Perk {

    @Nonnull
    @SerializedName(value = "Killer", alternate = {"PerkKiller"})
    public final String KILLER_NAME;

    private final Killer KILLER;

    KillerPerk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull Tiers tier, double rating, long ratings, @Nonnull String killerName) {
        super(name, tier, rating, ratings);
        KILLER_NAME = killerName;
        KILLER = KillersAPI.getKiller(killerName);
    }

    @Nonnull
    public static KillerPerk from(@Nonnull JsonElement json) {
        return API.GSON.fromJson(json.getAsJsonObject(), KillerPerk.class);
    }

    @Nonnull
    public String getKillerName() {
        return KILLER_NAME;
    }

    @Nullable
    @Override
    public ICharacter getCharacter() {
        return KILLER;
    }

}
