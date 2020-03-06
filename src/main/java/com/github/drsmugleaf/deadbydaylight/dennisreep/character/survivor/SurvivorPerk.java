package com.github.drsmugleaf.deadbydaylight.dennisreep.character.survivor;

import com.github.drsmugleaf.NonNull;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.deadbydaylight.dennisreep.API;
import com.github.drsmugleaf.deadbydaylight.dennisreep.character.Perk;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class SurvivorPerk extends Perk {

    @SerializedName("Survivor")
    private String SURVIVOR_NAME;

    public static SurvivorPerk from(JsonElement json) {
        JsonObject object = json.getAsJsonObject();
        return API.GSON.fromJson(object, SurvivorPerk.class);
    }

    @NonNull
    @Override
    public String getCharacterName() {
        return SURVIVOR_NAME;
    }

    @Nullable
    @Override
    public Survivor getCharacter() {
        return SurvivorsAPI.getSurvivors().get(SURVIVOR_NAME);
    }

}
