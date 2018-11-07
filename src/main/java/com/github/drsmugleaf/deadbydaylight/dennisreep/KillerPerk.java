package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    public static KillerPerk from(@Nonnull JsonElement json) {
        JsonObject object = json.getAsJsonObject();

        if (object.get("PerkName").getAsString().toLowerCase().equalsIgnoreCase("Barbecue & Chili")) {
            object.addProperty("PerkName", KillerPerks.BARBECUE_AND_CHILLI.NAME);
        }

        String killerName;
        if (object.has("Killer")) {
            killerName = object.get("Killer").getAsString();
        } else if (object.has("PerkKiller")) {
            killerName = object.get("PerkKiller").getAsString();
            object.remove("PerkKiller");
        } else {
            throw new IllegalArgumentException("No Killer or PerkKiller member found in json " + json);
        }

        killerName = NameResolver.resolveKillerName(killerName);
        object.addProperty("Killer", killerName);

        return API.GSON.fromJson(object, KillerPerk.class);
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
