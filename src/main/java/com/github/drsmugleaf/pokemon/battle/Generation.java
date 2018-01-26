package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 27/06/2017.
 */
public enum Generation {

    I("Generation I", "RB"),
    II("Generation II", "GS"),
    III("Generation III", "RS"),
    IV("Generation IV", "DP"),
    V("Generation V", "BW"),
    VI("Generation VI", "XY"),
    VII("Generation VII", "SM");

    static {
        I
                .setNewPokemons(151)
                .setTotalPokemons(151)
                .setCoreGames(
                        Game.RED_AND_GREEN,
                        Game.BLUE,
                        Game.RED_AND_BLUE,
                        Game.YELLOW
                );

        II
                .setNewPokemons(100)
                .setTotalPokemons(251)
                .setCoreGames(
                        Game.GOLD_AND_SILVER,
                        Game.CRYSTAL
                );

        III
                .setNewPokemons(135)
                .setTotalPokemons(386)
                .setCoreGames(
                        Game.RUBY_AND_SAPPHIRE,
                        Game.FIRERED_AND_LEAFGREEN,
                        Game.EMERALD
                );

        IV
                .setNewPokemons(107)
                .setTotalPokemons(493)
                .setCoreGames(
                        Game.DIAMOND_AND_PEARL,
                        Game.PLATINUM,
                        Game.HEARTGOLD_AND_SOULSILVER
                );

        V
                .setNewPokemons(156)
                .setTotalPokemons(649)
                .setCoreGames(
                        Game.BLACK_AND_WHITE,
                        Game.BLACK_2_AND_WHITE_2
                );

        VI
                .setNewPokemons(72)
                .setTotalPokemons(721)
                .setCoreGames(
                        Game.X_AND_Y,
                        Game.OMEGA_RUBY_AND_ALPHA_SAPPHIRE
                );

        VII
                .setNewPokemons(81)
                .setTotalPokemons(802)
                .setCoreGames(
                        Game.SUN_AND_MOON,
                        Game.ULTRA_SUN_AND_ULTRA_MOON
                );
    }

    private final String NAME;
    private final String SHORTHAND;
    private final List<Game> CORE_GAMES = new ArrayList<>();
    private int NEW_POKEMONS;
    private int TOTAL_POKEMONS;

    Generation(@Nonnull String name, String shorthand) {
        Holder.MAP.put(shorthand.toLowerCase(), this);
        NAME = name;
        SHORTHAND = shorthand;
    }

    @Nonnull
    public static Generation getGeneration(@Nonnull String shorthand) {
        shorthand = shorthand.toLowerCase();
        if (!Holder.MAP.containsKey(shorthand)) {
            throw new NullPointerException("Generation " + shorthand + " doesn't exist");
        }

        return Holder.MAP.get(shorthand);
    }

    @Contract(pure = true)
    @Nonnull
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    @Nonnull
    public String getShorthand() {
        return SHORTHAND;
    }

    @Contract(pure = true)
    public int getNewPokemons() {
        return NEW_POKEMONS;
    }

    @Nonnull
    private Generation setNewPokemons(int amount) {
        NEW_POKEMONS = amount;
        return this;
    }

    @Contract(pure = true)
    public int getTotalPokemons() {
        return TOTAL_POKEMONS;
    }

    @Nonnull
    private Generation setTotalPokemons(int amount) {
        TOTAL_POKEMONS = amount;
        return this;
    }

    @Nonnull
    public List<Game> getCoreGames() {
        return new ArrayList<>(CORE_GAMES);
    }

    @Nonnull
    private Generation setCoreGames(@Nonnull Game... games) {
        Collections.addAll(CORE_GAMES, games);
        return this;
    }

    private static class Holder {
        static Map<String, Generation> MAP = new HashMap<>();
    }

}
