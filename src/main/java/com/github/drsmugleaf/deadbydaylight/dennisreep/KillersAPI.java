package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillersAPI extends API {

    @Nonnull
    private static final String KILLER_DATA_ENDPOINT = "getKillerData/";

    @Nonnull
    private static final String KILLER_PERK_DATA_ENDPOINT = "getKillerPerkData/";

    @Nonnull
    private static final Supplier<Map<String, Killer>> KILLERS = Suppliers.memoizeWithExpiration(
            KillersAPI::getKillerData, 12, TimeUnit.HOURS
    );

    @Nonnull
    private static final Supplier<PerkList<KillerPerk>> GLOBAL_KILLER_PERKS = Suppliers.memoizeWithExpiration(
            KillersAPI::getKillerPerkData, 12, TimeUnit.HOURS
    );

    @Nonnull
    private static final Cache<Killer, PerkList<KillerPerk>> SPECIFIC_KILLER_PERKS = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(CacheLoader.from(killer -> getKillerData(killer)));

    private KillersAPI() {}

    @Nonnull
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

    @Nonnull
    private static PerkList<KillerPerk> getKillerPerkData() {
        JsonElement json = getResponse(KILLER_PERK_DATA_ENDPOINT).getAsJsonArray("KillerPerk");
        KillerPerk[] perkArray = GSON.fromJson(json, KillerPerk[].class);
        List<KillerPerk> perks = Arrays.asList(perkArray);
        return new PerkList<>(perks);
    }

    @Nonnull
    private static PerkList<KillerPerk> getKillerData(@Nonnull Killer killer) {
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

    @Nonnull
    public static Map<String, Killer> getKillers() {
        return KILLERS.get();
    }

    @Nonnull
    public static PerkList<KillerPerk> getPerks() {
        return GLOBAL_KILLER_PERKS.get();
    }

    @Nonnull
    public static PerkList<KillerPerk> getPerks(@Nonnull Killer killer) {
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

    @Nonnull
    public static Killer randomKiller() {
        List<Killer> killers = new ArrayList<>(KILLERS.get().values());
        int index = ThreadLocalRandom.current().nextInt(1, KILLERS.get().size());
        return killers.get(index);
    }

}
