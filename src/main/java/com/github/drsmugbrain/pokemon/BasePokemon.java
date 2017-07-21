package com.github.drsmugbrain.pokemon;

import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public class BasePokemon implements Comparable<BasePokemon> {

    private static final Map<String, BasePokemon> BASE_POKEMON = new TreeMap<>();

    private final String NAME;

    private final Ability[] BASE_ABILITIES;

    private final Type[] TYPES;

    private final Map<Stat, Integer> BASE_STATS;

    private final int WEIGHT; // TODO: Parse pokemon weights

    protected BasePokemon(@Nonnull String name, @Nonnull Ability[] abilities, @Nonnull Type[] types, @Nonnull Map<Stat, Integer> stats, int weight) {
        this.NAME = name;
        this.BASE_ABILITIES = abilities;
        this.TYPES = types;
        this.BASE_STATS = stats;
        this.WEIGHT = weight;
    }

    protected BasePokemon(@Nonnull String name, @Nonnull Ability[] abilities, @Nonnull Type[] types, @Nonnull Map<Stat, Integer> stats) {
        this(name, abilities, types, stats, 100);
    }

    protected BasePokemon(@Nonnull BasePokemon basePokemon) {
        this(basePokemon.getName(), basePokemon.getBaseAbilities(), basePokemon.getTypes(), basePokemon.getBaseStats(), 100);
    }

    @Nonnull
    public static BasePokemon getBasePokemon(@Nonnull String name) {
        if (!BasePokemon.BASE_POKEMON.containsKey(name)) {
            throw new NullPointerException("Pokemon " + name + " doesn't exist");
        }

        return BasePokemon.BASE_POKEMON.get(name);
    }

    @Nonnull
    public static Map<Stat, Integer> parseBaseStats(@Nonnull JSONObject stats) {
        Map<Stat, Integer> statsMap = new HashMap<>();

        statsMap.put(Stat.HP, stats.getInt("hp"));
        statsMap.put(Stat.ATTACK, stats.getInt("atk"));
        statsMap.put(Stat.DEFENSE, stats.getInt("def"));
        statsMap.put(Stat.SPEED, stats.getInt("spe"));
        statsMap.put(Stat.SPECIAL_ATTACK, stats.getInt("spa"));
        statsMap.put(Stat.SPECIAL_DEFENSE, stats.getInt("spd"));
        statsMap.put(Stat.ACCURACY, 0);
        statsMap.put(Stat.EVASION, 0);

        return statsMap;
    }

    @Override
    public int compareTo(@Nonnull BasePokemon pokemon) {
        return this.NAME.compareTo(pokemon.NAME);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Ability[] getBaseAbilities() {
        return this.BASE_ABILITIES;
    }

    @Nonnull
    public Type[] getTypes() {
        return this.TYPES;
    }

    protected void createBasePokemon() {
        BasePokemon.BASE_POKEMON.put(this.NAME, this);
    }

    @Nonnull
    public Map<Stat, Integer> getBaseStats() {
        return this.BASE_STATS;
    }

    public int getBaseStat(@Nonnull Stat stat) {
        return this.BASE_STATS.get(stat);
    }

    public int getBaseWeight() {
        return this.WEIGHT;
    }

}
