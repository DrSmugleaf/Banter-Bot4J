package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class SurvivorPerk extends Perk {

    @SerializedName("Survivor")
    public final String SURVIVOR_NAME;

    SurvivorPerk(String name, Tiers tier, double rating, long ratings, String survivorName) {
        super(name, tier, rating, ratings);
        SURVIVOR_NAME = survivorName;
    }

    public static SurvivorPerk from(JsonElement json) {
        JsonObject object = json.getAsJsonObject();
        return API.GSON.fromJson(object, SurvivorPerk.class);
    }

    @Nullable
    @Override
    public ICharacter getCharacter() {
        return null;
    }

}
