package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.KillerPerks;
import com.github.drsmugleaf.deadbydaylight.SurvivorPerks;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
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
    public static final Supplier<Perks<KillerPerks, KillerPerk>> KILLER_PERKS = Suppliers.memoizeWithExpiration(
            PerksAPI::getKillerPerkData, 12, TimeUnit.HOURS
    );

    @Nonnull
    public static final Supplier<Perks<SurvivorPerks, SurvivorPerk>> SURVIVOR_PERKS = Suppliers.memoizeWithExpiration(
            PerksAPI::getSurvivorPerkData, 12, TimeUnit.HOURS
    );

    private PerksAPI() {}

    @Nonnull
    private static Perks<KillerPerks, KillerPerk> getKillerPerkData() {
        JsonArray json = getResponse(KILLER_PERK_DATA_ENDPOINT).get("KillerPerk").getAsJsonArray();
        List<KillerPerk> perkList = new ArrayList<>();
        for (JsonElement element : json) {
            KillerPerk perk = KillerPerk.from(element);
            perkList.add(perk);
        }

        Map<KillerPerks, KillerPerk> perks = perkList.stream().collect(Collectors.toMap(KillerPerk::toPerk, perk -> perk));
        return new Perks<>(perks);
    }

    @Nonnull
    private static Perks<SurvivorPerks, SurvivorPerk> getSurvivorPerkData() {
        JsonArray json = getResponse(SURVIVOR_PERK_DATA_ENDPOINT).get("SurvivorPerk").getAsJsonArray();
        List<SurvivorPerk> perkList = new ArrayList<>();
        for (JsonElement element : json) {
            SurvivorPerk perk = SurvivorPerk.from(element);
            perkList.add(perk);
        }

        Map<SurvivorPerks, SurvivorPerk> perks = perkList.stream().collect(Collectors.toMap(SurvivorPerk::toPerk, perk -> perk));
        return new Perks<>(perks);
    }

}
