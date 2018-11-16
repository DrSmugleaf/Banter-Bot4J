package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillersAPI extends API {

    @NotNull
    private static final String KILLER_DATA_ENDPOINT = "getKillerData/";

    @NotNull
    public static final Supplier<Map<Killers, Killer>> KILLERS = Suppliers.memoizeWithExpiration(
            KillersAPI::getKillerData, 12, TimeUnit.HOURS
    );

    @NotNull
    private static final Cache<Killers, Perks<KillerPerks, KillerPerk>> KILLER_PERKS = CacheBuilder
            .newBuilder()
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build(CacheLoader.from((Killers killer) -> getKillerData(killer)));

    private KillersAPI() {}

    @NotNull
    private static Map<Killers, Killer> getKillerData() {
        JsonArray json = getResponse(KILLER_DATA_ENDPOINT).get("Killers").getAsJsonArray();
        Map<Killers, Killer> killers = new LinkedHashMap<>();
        for (JsonElement element : json) {
            Killer killer = Killer.from(element);
            killers.put(killer.KILLER, killer);
        }

        return killers;
    }

    @NotNull
    public static Perks<KillerPerks, KillerPerk> getKillerData(@NotNull Killers killer) {
        String endpoint = KILLER_DATA_ENDPOINT + "?killer=" + killer.NAME;
        JsonArray json = getResponse(endpoint).get("Killers").getAsJsonArray();
        Map<KillerPerks, KillerPerk> perks = new HashMap<>();
        for (JsonElement element : json) {
            KillerPerk perk = KillerPerk.from(element);
            perks.put(perk.toPerk(), perk);
        }

        return new Perks<>(perks);
    }

}
