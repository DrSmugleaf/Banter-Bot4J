package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.github.drsmugleaf.deadbydaylight.Survivors;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class SurvivorPerk extends Perk {

    @Nonnull
    @SerializedName("Survivor")
    public final Survivors SURVIVOR;

    SurvivorPerk(@Nonnull String imageUrl, @Nonnull String name, @Nonnull Tiers tier, double rating, long ratings, @Nonnull Survivors survivor) {
        super(imageUrl, name, tier, rating, ratings);
        SURVIVOR = survivor;
    }

    @Nonnull
    public static SurvivorPerk from(@Nonnull JsonElement json) {
        JsonObject object = json.getAsJsonObject();

        if (object.get("Image").getAsString().toLowerCase().contains("dejavu")) {
            object.addProperty("PerkName", SurvivorPerks.DEJA_VU.NAME);
        }

        if (object.get("PerkName").getAsString().equalsIgnoreCase("Open Handed")) {
            object.addProperty("PerkName", SurvivorPerks.OPEN_HANDED.NAME);
        }

        String survivor = object.get("Survivor").getAsString();
        survivor = NameResolver.resolveSurvivorName(survivor);
        object.addProperty("Survivor", survivor);

        return API.GSON.fromJson(object, SurvivorPerk.class);
    }

    @Nonnull
    public static SurvivorPerk from(@Nonnull SurvivorPerks perk) {
        return PerksAPI.getSurvivorPerkData().get(perk);
    }

    @Nonnull
    @Override
    public SurvivorPerks toPerk() {
        String name = NAME;
        if (name.equalsIgnoreCase("Open Handed")) {
            name = "Open-Handed";
        }

        return SurvivorPerks.from(name);
    }

}
