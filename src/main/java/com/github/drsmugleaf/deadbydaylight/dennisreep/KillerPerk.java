package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillerPerk extends Perk {

    @SerializedName(value = "Killer", alternate = {"PerkKiller"})
    public final String KILLER_NAME;

    private final Killer KILLER;

    KillerPerk(String imageUrl, String name, Tiers tier, double rating, long ratings, String killerName) {
        super(name, tier, rating, ratings);
        KILLER_NAME = killerName;
        KILLER = KillersAPI.getKiller(killerName);
    }

    public static KillerPerk from(JsonElement json) {
        return API.GSON.fromJson(json.getAsJsonObject(), KillerPerk.class);
    }

    public String getKillerName() {
        return KILLER_NAME;
    }

    @Nullable
    @Override
    public ICharacter getCharacter() {
        return KILLER;
    }

}
