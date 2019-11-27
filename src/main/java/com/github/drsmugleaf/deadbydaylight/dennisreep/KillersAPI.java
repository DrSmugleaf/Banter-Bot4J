package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.Nullable;
import com.google.common.base.Suppliers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillersAPI extends API {

    private static final String KILLER_DATA_ENDPOINT = "getKillerData/";
    private static final String KILLER_PERK_DATA_ENDPOINT = "getKillerPerkData/";
    private static final Supplier<Map<String, Killer>> KILLERS = Suppliers.memoizeWithExpiration(
            KillersAPI::getKillerData, 12, TimeUnit.HOURS
    )::get;
    private static final Supplier<PerkList<KillerPerk>> GLOBAL_KILLER_PERKS = Suppliers.memoizeWithExpiration(
            KillersAPI::getKillerPerkData, 12, TimeUnit.HOURS
    )::get;
    private static final Cache<Killer, PerkList<KillerPerk>> SPECIFIC_KILLER_PERKS = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(CacheLoader.from(killer -> getKillerData(killer)));

    private KillersAPI() {}

    private static Map<String, Killer> getKillerData() {
        JsonArray json = getResponse(KILLER_DATA_ENDPOINT).get("Killers").getAsJsonArray();
        Map<String, Killer> killers = new LinkedHashMap<>();
        for (JsonElement element : json) {
            Killer killer = Killer.from(element);
            String killerName = killer.NAME;
            killers.put(killerName, killer);
        }

        return killers;
    }

    private static PerkList<KillerPerk> getKillerPerkData() {
        JsonElement json = getResponse(KILLER_PERK_DATA_ENDPOINT).getAsJsonArray("KillerPerk");
        KillerPerk[] perkArray = GSON.fromJson(json, KillerPerk[].class);
        List<KillerPerk> perks = Arrays.asList(perkArray);
        return new PerkList<>(perks);
    }

    private static PerkList<KillerPerk> getKillerData(Killer killer) {
        String endpoint = KILLER_DATA_ENDPOINT + "?killer=" + killer.getName();
        JsonObject response = getResponse(endpoint);
        JsonArray json = response.get("Killers").getAsJsonArray();
        PerkList<KillerPerk> perks = new PerkList<>();
        for (JsonElement element : json) {
            KillerPerk perk = KillerPerk.from(element);
            perks.add(perk);
        }

        return perks;
    }

    public static Map<String, Killer> getKillers() {
        return KILLERS.get();
    }

    public static PerkList<KillerPerk> getPerks() {
        return GLOBAL_KILLER_PERKS.get();
    }

    public static PerkList<KillerPerk> getPerks(Killer killer) {
        try {
            return SPECIFIC_KILLER_PERKS.get(killer, () -> getKillerData(killer));
        } catch (ExecutionException e) {
            throw new IllegalStateException("Error getting perks for killer " + killer.getName());
        }
    }

    @Nullable
    public static Killer getKiller(@Nullable String name) {
        for (Map.Entry<String, Killer> entry : getKillers().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public static Killer randomKiller() {
        List<Killer> killers = new ArrayList<>(KILLERS.get().values());
        int index = ThreadLocalRandom.current().nextInt(1, KILLERS.get().size());
        return killers.get(index);
    }

}
