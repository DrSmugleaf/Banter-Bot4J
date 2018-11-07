package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class PerksAPI extends API {

    @Nonnull
    private static final String KILLER_PERK_DATA_ENDPOINT = "getKillerPerkData/";

    @Nonnull
    private static final String SURVIVOR_PERK_DATA_ENDPOINT = "getSurvivorPerkData/";

    @Nonnull
    private static final Cache<KillerPerks, KillerPerk> KILLER_PERKS = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    @Nonnull
    private static final Cache<SurvivorPerks, SurvivorPerk> SURVIVOR_PERKS = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    private PerksAPI() {}

    @Nonnull
    public static Map<KillerPerks, KillerPerk> getKillerPerkData() {
        if (KILLER_PERKS.size() == KillerPerks.values().length) {
            return KILLER_PERKS.asMap();
        }

        JsonArray json = getResponse(KILLER_PERK_DATA_ENDPOINT).get("KillerPerk").getAsJsonArray();
        List<KillerPerk> perkList = new ArrayList<>();
        for (JsonElement element : json) {
            KillerPerk perk = KillerPerk.from(element);
            perkList.add(perk);
        }

        Map<KillerPerks, KillerPerk> perks = perkList.stream().collect(Collectors.toMap(KillerPerk::toPerk, perk -> perk));
        KILLER_PERKS.putAll(perks);
        return KILLER_PERKS.asMap();
    }

    @Nonnull
    public static Map<SurvivorPerks, SurvivorPerk> getSurvivorPerkData() {
        if (SURVIVOR_PERKS.size() == SurvivorPerks.values().length) {
            return SURVIVOR_PERKS.asMap();
        }

        JsonArray json = getResponse(SURVIVOR_PERK_DATA_ENDPOINT).get("SurvivorPerk").getAsJsonArray();
        List<SurvivorPerk> perkList = new ArrayList<>();
        for (JsonElement element : json) {
            SurvivorPerk perk = SurvivorPerk.from(element);
            perkList.add(perk);
        }

        Map<SurvivorPerks, SurvivorPerk> perks = perkList.stream().collect(Collectors.toMap(SurvivorPerk::toPerk, perk -> perk));
        SURVIVOR_PERKS.putAll(perks);
        return SURVIVOR_PERKS.asMap();
    }

}
