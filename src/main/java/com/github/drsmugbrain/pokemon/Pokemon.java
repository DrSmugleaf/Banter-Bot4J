package com.github.drsmugbrain.pokemon;

import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon extends BasePokemon {
    private final String NICKNAME;

    private final Ability ABILITY;

    private final int LEVEL;

    private final Type[] TYPES;

    private final Map<Stat, Integer> STATS;


    public Pokemon(@Nonnull BasePokemon basePokemon, @Nonnull Ability ability, int level, Map<Stat, Integer> stats) {
        super(basePokemon);

        this.NICKNAME = basePokemon.getName();

        this.ABILITY = ability;

        this.LEVEL = level;

        this.TYPES = basePokemon.getTypes();

        this.STATS = stats;
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

    @Nonnull
    public String getNickname() {
        return this.NICKNAME;
    }

    @Nonnull
    public Ability getAbility() {
        return this.ABILITY;
    }

    @Nonnull
    public Type[] getTypes() {
        return this.TYPES;
    }

    @Nonnull
    public Map<Stat, Integer> getStats() {
        return this.STATS;
    }

    public int getLevel() {
        return this.LEVEL;
    }

    public int getHP() {
        return this.STATS.get(Stat.HP);
    }

    public int getAttack() {
        return this.STATS.get(Stat.ATTACK);
    }

    public int getDefense() {
        return this.STATS.get(Stat.DEFENSE);
    }

    public int getSpeed() {
        return this.STATS.get(Stat.SPEED);
    }

    public int getSpecialAttack() {
        return this.STATS.get(Stat.SPECIAL_ATTACK);
    }

    public int getSpecialDefense() {
        return this.STATS.get(Stat.SPECIAL_DEFENSE);
    }

    public int getAccuracy() {
        return this.STATS.get(Stat.ACCURACY);
    }

    public int getEvasion() {
        return this.STATS.get(Stat.EVASION);
    }

}
