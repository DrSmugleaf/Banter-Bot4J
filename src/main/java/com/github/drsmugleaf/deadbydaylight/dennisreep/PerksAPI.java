package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class PerksAPI extends API {

    @Nonnull
    private static final String KILLER_PERK_DATA_ENDPOINT = "getKillerPerkData/";

    @Nonnull
    private static final String SURVIVOR_PERK_DATA_ENDPOINT = "getSurvivorPerkData/";

    private PerksAPI() {}

    @Nonnull
    public static Map<KillerPerks, KillerPerk> getKillerPerkData() {
        JsonArray json = getResponse(KILLER_PERK_DATA_ENDPOINT).get("KillerPerk").getAsJsonArray();
        List<KillerPerk> perkList = GSON.fromJson(json, new TypeToken<ArrayList<KillerPerk>>(){}.getType());
        return perkList.stream().collect(Collectors.toMap(KillerPerk::toPerk, perk -> perk));
    }

    @Nonnull
    public static Map<SurvivorPerks, SurvivorPerk> getSurvivorPerkData() {
        JsonArray json = getResponse(SURVIVOR_PERK_DATA_ENDPOINT).get("SurvivorPerk").getAsJsonArray();
        List<SurvivorPerk> perkList = GSON.fromJson(json, new TypeToken<ArrayList<SurvivorPerk>>(){}.getType());
        return perkList.stream().collect(Collectors.toMap(SurvivorPerk::toPerk, perk -> perk));
    }

}
