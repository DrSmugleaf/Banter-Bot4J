package com.github.drsmugleaf.deadbydaylight.dennisreep.character.survivor;

import com.github.drsmugleaf.deadbydaylight.dennisreep.API;
import com.github.drsmugleaf.deadbydaylight.dennisreep.character.PerkList;
import com.google.common.base.Suppliers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by DrSmugleaf on 21/04/2019
 */
public class SurvivorsAPI extends API {

    private static final String SURVIVOR_PERK_DATA_ENDPOINT = "getSurvivorPerkData/";
    private static final Supplier<Map<String, Survivor>> SURVIVORS = Suppliers.memoizeWithExpiration(
            SurvivorsAPI::getSurvivorData, 12, TimeUnit.HOURS
    )::get;
    private static final Supplier<Map<String, String>> SURVIVOR_NAMES = Suppliers.memoizeWithExpiration(
            SurvivorsAPI::getSurvivorNames, 12, TimeUnit.HOURS
    )::get;
    private static final Supplier<PerkList<SurvivorPerk>> PERKS = Suppliers.memoizeWithExpiration(
            SurvivorsAPI::getPerkData, 12, TimeUnit.HOURS
    )::get;

    private SurvivorsAPI() {}

    private static Map<String, Survivor> getSurvivorData() {
        Map<String, Survivor> survivors = new HashMap<>();
        for (SurvivorPerk perk : getPerks()) {
            String survivorName = perk.getCharacterName();
            if (survivorName.equalsIgnoreCase("all")) {
                continue;
            }

            Survivor survivor = new Survivor(survivorName, IMAGES_PATH + "/survivors/" + survivorName.toLowerCase() + ".png");
            survivors.put(survivorName, survivor);
        }

        return survivors;
    }

    private static Map<String, String> getSurvivorNames() {
        Map<String, String> names = new HashMap<>();
        for (String originalName : getSurvivors().keySet()) {
            String name = originalName.toLowerCase();
            names.put(name, originalName);

            String[] nameArray = name.split(" ");
            String firstName = nameArray[0];
            if (!firstName.equals(name) && ! names.containsKey(firstName)) {
                names.put(firstName, originalName);
            }
        }

        return names;
    }

    private static PerkList<SurvivorPerk> getPerkData() {
        JsonArray json = getResponse(SURVIVOR_PERK_DATA_ENDPOINT).get("SurvivorPerk").getAsJsonArray();
        PerkList<SurvivorPerk> perks = new PerkList<>();
        for (JsonElement element : json) {
            SurvivorPerk perk = SurvivorPerk.from(element);
            perks.add(perk);
        }

        return perks;
    }

    public static Map<String, Survivor> getSurvivors() {
        return SURVIVORS.get();
    }

    public static PerkList<SurvivorPerk> getPerks() {
        return PERKS.get();
    }

    public static Survivor randomSurvivor() {
        List<Survivor> survivors = new ArrayList<>(getSurvivors().values());
        int index = ThreadLocalRandom.current().nextInt(survivors.size());
        return survivors.get(index);
    }

}
