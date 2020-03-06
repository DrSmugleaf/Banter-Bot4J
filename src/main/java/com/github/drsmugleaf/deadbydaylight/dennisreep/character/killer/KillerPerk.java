package com.github.drsmugleaf.deadbydaylight.dennisreep.character.killer;

import com.github.drsmugleaf.NonNull;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.deadbydaylight.dennisreep.API;
import com.github.drsmugleaf.deadbydaylight.dennisreep.character.Perk;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillerPerk extends Perk {

    @SerializedName(value = "Killer", alternate = {"PerkKiller"})
    private String KILLER_NAME;

    public static KillerPerk from(JsonElement json) {
        return API.GSON.fromJson(json.getAsJsonObject(), KillerPerk.class);
    }

    @NonNull
    @Override
    public String getCharacterName() {
        return KILLER_NAME;
    }

    @Nullable
    @Override
    public Killer getCharacter() {
        return KillersAPI.getKillers().get(KILLER_NAME);
    }

}
