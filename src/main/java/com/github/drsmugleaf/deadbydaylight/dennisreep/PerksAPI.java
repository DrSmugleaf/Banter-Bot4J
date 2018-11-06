package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class PerksAPI extends API {

    private PerksAPI() {}

    @Nonnull
    public static List<KillerPerk> getKillerPerkData() {
        JsonArray json = getResponse("getKillerPerkData/").get("KillerPerk").getAsJsonArray();
        return GSON.fromJson(json, new TypeToken<ArrayList<KillerPerk>>(){}.getType());
    }

    @Nonnull
    public static List<SurvivorPerk> getSurvivorPerkData() {
        JsonArray json = getResponse("getSurvivorPerkData/").get("SurvivorPerk").getAsJsonArray();
        return GSON.fromJson(json, new TypeToken<ArrayList<SurvivorPerk>>(){}.getType());
    }

}
