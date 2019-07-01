package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 27/06/2017.
 */
public enum Game {

    RED_AND_GREEN("Red and Green"),
    BLUE("Blue"),
    RED_AND_BLUE("Red and Blue"),
    YELLOW("Yellow"),
    STADIUM("Stadium"),

    GOLD_AND_SILVER("Gold and Silver"),
    CRYSTAL("Crystal"),

    RUBY_AND_SAPPHIRE("Ruby and Sapphire"),
    FIRERED_AND_LEAFGREEN("FireRed and LeafGreen"),
    EMERALD("Emerald"),

    DIAMOND_AND_PEARL("Diamond and Pearl"),
    PLATINUM("Platinum"),
    HEARTGOLD_AND_SOULSILVER("HeartGold and SoulSilver"),

    BLACK_AND_WHITE("Black and White"),
    BLACK_2_AND_WHITE_2("Black 2 and White 2"),

    X_AND_Y("X and Y"),
    OMEGA_RUBY_AND_ALPHA_SAPPHIRE("Omega Ruby and Alpha Sapphire"),

    SUN_AND_MOON("Sun and Moon"),
    ULTRA_SUN_AND_ULTRA_MOON("Ultra Sun and Ultra Moon");

    public final String NAME;

    Game(String name) {
        NAME = name;
    }

    public static Game getGame(String game) {
        game = game.toLowerCase();

        if (!Holder.MAP.containsKey(game)) {
            throw new NullPointerException("Game " + game + " doesn't exist");
        }

        return Holder.MAP.get(game);
    }

    public static Game getLatest() {
        return Holder.MAP.lastEntry().getValue();
    }

    public static Game getLatest(Collection<Game> games) {
        return games
                .stream()
                .max(Comparator.comparingInt(Enum::ordinal))
                .orElseGet(Game::getLatest);
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    private static class Holder {
        static NavigableMap<String, Game> MAP = new TreeMap<>();
    }

}
