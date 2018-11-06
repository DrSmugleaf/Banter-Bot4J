package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Killers;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class KillersAPI extends API {

    private static final String KILLER_DATA_ENDPOINT = "getKillerData/";

    private KillersAPI() {}

    @Nonnull
    public static List<Killer> getKillerData() {
        JsonArray json = getResponse(KILLER_DATA_ENDPOINT).get("Killers").getAsJsonArray();
        return GSON.fromJson(json, new TypeToken<ArrayList<Killer>>(){}.getType());
    }

    @Nonnull
    public static List<KillerPerk> getKillerData(@Nonnull Killers killer) {
        String endpoint = KILLER_DATA_ENDPOINT + "?killer=" + killer.NICKNAME;
        JsonArray json = getResponse(endpoint).get("Killers").getAsJsonArray();
        return GSON.fromJson(json, new TypeToken<ArrayList<KillerPerk>>(){}.getType());
    }

}
