package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.github.drsmugleaf.deadbydaylight.Survivors;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class SurvivorPerk extends Perk {

    @NotNull
    @SerializedName("Survivor")
    public final Survivors SURVIVOR;

    SurvivorPerk(@NotNull String imageUrl, @NotNull String name, @NotNull Tiers tier, double rating, long ratings, @NotNull Survivors survivor) {
        super(name, tier, rating, ratings);
        SURVIVOR = survivor;
    }

    @NotNull
    public static SurvivorPerk from(@NotNull JsonElement json) {
        JsonObject object = json.getAsJsonObject();

        if (object.get("Image").getAsString().toLowerCase().contains("dejavu")) {
            object.addProperty("PerkName", SurvivorPerks.DEJA_VU.NAME);
        }

        if (object.get("PerkName").getAsString().equalsIgnoreCase("Open Handed")) {
            object.addProperty("PerkName", SurvivorPerks.OPEN_HANDED.NAME);
        }

        String survivorName = object.get("Survivor").getAsString();
        survivorName = NameResolver.resolveSurvivorName(survivorName);
        object.addProperty("Survivor", survivorName);

        return API.GSON.fromJson(object, SurvivorPerk.class);
    }

    @NotNull
    public static SurvivorPerk from(@NotNull SurvivorPerks perk) {
        return PerksAPI.SURVIVOR_PERKS.get().get(perk);
    }

    @NotNull
    @Override
    public ICharacter getCharacter() {
        return SURVIVOR;
    }

    @NotNull
    @Override
    public SurvivorPerks toPerk() {
        return SurvivorPerks.from(NAME);
    }

}
