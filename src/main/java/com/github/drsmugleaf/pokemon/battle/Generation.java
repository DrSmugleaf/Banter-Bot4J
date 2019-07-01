package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.Contract;

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
                )
                .setSideGames(Game.STADIUM);

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

    public final String NAME;
    public final String SHORTHAND;
    private final List<Game> CORE_GAMES = new ArrayList<>();
    private final List<Game> SIDE_GAMES = new ArrayList<>();
    private int NEW_POKEMONS;
    private int TOTAL_POKEMONS;

    Generation(String name, String shorthand) {
        Holder.MAP.put(shorthand.toLowerCase(), this);
        NAME = name;
        SHORTHAND = shorthand;
    }

    public static Generation getGeneration(String shorthand) {
        shorthand = shorthand.toLowerCase();

        if (!Holder.MAP.containsKey(shorthand)) {
            throw new NullPointerException("Generation " + shorthand + " doesn't exist");
        }

        return Holder.MAP.get(shorthand);
    }

    public static Generation getLatest() {
        return Holder.MAP.lastEntry().getValue();
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public String getShorthand() {
        return SHORTHAND;
    }

    @Contract(pure = true)
    public int getNewPokemons() {
        return NEW_POKEMONS;
    }

    @Contract("_ -> this")
    private Generation setNewPokemons(int amount) {
        NEW_POKEMONS = amount;
        return this;
    }

    @Contract(pure = true)
    public int getTotalPokemons() {
        return TOTAL_POKEMONS;
    }

    @Contract("_ -> this")
    private Generation setTotalPokemons(int amount) {
        TOTAL_POKEMONS = amount;
        return this;
    }

    @Contract(" -> new")
    public List<Game> getCoreGames() {
        return new ArrayList<>(CORE_GAMES);
    }

    @Contract("_ -> this")
    private Generation setCoreGames(Game... games) {
        Collections.addAll(CORE_GAMES, games);
        return this;
    }

    @Contract(" -> new")
    public List<Game> getSideGames() {
        return new ArrayList<>(SIDE_GAMES);
    }

    @Contract("_ -> this")
    private Generation setSideGames(Game... games) {
        Collections.addAll(SIDE_GAMES, games);
        return this;
    }

    public boolean isBefore(Generation generation) {
        return ordinal() < generation.ordinal();
    }

    public boolean isAfter(Generation generation) {
        return ordinal() > generation.ordinal();
    }

    private static class Holder {
        static NavigableMap<String, Generation> MAP = new TreeMap<>();
    }

}
