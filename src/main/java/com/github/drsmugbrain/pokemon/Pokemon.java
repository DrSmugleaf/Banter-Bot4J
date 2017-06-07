package com.github.drsmugbrain.pokemon;

import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon implements Comparable<Pokemon> {

    private static final Set<Pokemon> BASE_POKEMON = new TreeSet<>();

    private final String NAME;
    private final String NICKNAME;

    private final Type[] TYPES;

    private final Map<Stat, Integer> BASE_STATS;
    private final Map<Stat, Integer> STATS;

    public Pokemon(@Nonnull String name, @Nonnull Type[] types, @Nonnull Map<Stat, Integer> stats) {
        this.NAME = name;
        this.NICKNAME = name;

        this.TYPES = types;

        this.BASE_STATS = stats;
        this.STATS = stats;
    }

    static void createBasePokemon(@Nonnull String name, @Nonnull Type[] types, @Nonnull Map<Stat, Integer> stats) {
        Pokemon.BASE_POKEMON.add(new Pokemon(name, types, stats));
    }

    @Nonnull
    public static Set<Pokemon> getBasePokemon() {
        return Pokemon.BASE_POKEMON;
    }

    @Nonnull
    public static Map<Stat, Integer> parseStats(@Nonnull JSONObject stats) {
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
    public int compareTo(@Nonnull Pokemon pokemon) {
        return this.NAME.compareTo(pokemon.NAME);
    }

    @Nonnull
    public Type[] getTypes() {
        return this.TYPES;
    }

}
