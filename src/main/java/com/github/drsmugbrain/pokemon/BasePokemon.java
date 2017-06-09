package com.github.drsmugbrain.pokemon;

import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public class BasePokemon implements Comparable<BasePokemon> {

    private static final Set<BasePokemon> BASE_POKEMON = new TreeSet<>();

    private final String NAME;

    private final Ability[] BASE_ABILITIES;

    private final Type[] TYPES;

    private final Map<Stat, Integer> BASE_STATS;

    protected BasePokemon(@Nonnull String name, @Nonnull Ability[] abilities, @Nonnull Type[] types, @Nonnull Map<Stat, Integer> stats) {
        this.NAME = name;
        this.BASE_ABILITIES = abilities;
        this.TYPES = types;
        this.BASE_STATS = stats;
    }

    protected BasePokemon(@Nonnull BasePokemon basePokemon) {
        this(basePokemon.getName(), basePokemon.getBaseAbilities(), basePokemon.getTypes(), basePokemon.getBaseStats());
    }

    @Nonnull
    public static Set<BasePokemon> getBasePokemon() {
        return BasePokemon.BASE_POKEMON;
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
        BasePokemon.BASE_POKEMON.add(this);
    }

    @Nonnull
    public Map<Stat, Integer> getBaseStats() {
        return this.BASE_STATS;
    }

    public int getBaseStat(@Nonnull Stat stat) {
        return this.BASE_STATS.get(stat);
    }

}
