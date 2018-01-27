package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.stats.PermanentStat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum Nature {

    HARDY(0, "Hardy", null, null),
    LONELY(1, "Lonely", PermanentStat.ATTACK, PermanentStat.DEFENSE),
    BRAVE(2, "Brave", PermanentStat.ATTACK, PermanentStat.SPEED),
    ADAMANT(3, "Adamant", PermanentStat.ATTACK, PermanentStat.SPECIAL_ATTACK),
    NAUGHTY(4, "Naughty", PermanentStat.ATTACK, PermanentStat.SPECIAL_DEFENSE),
    BOLD(5, "Bold", PermanentStat.DEFENSE, PermanentStat.ATTACK),
    DOCILE(6, "Docile", null, null),
    RELAXED(7, "Relaxed", PermanentStat.DEFENSE, PermanentStat.SPEED),
    IMPISH(8, "Impish", PermanentStat.DEFENSE, PermanentStat.SPECIAL_ATTACK),
    LAX(9, "Lax", PermanentStat.DEFENSE, PermanentStat.SPECIAL_DEFENSE),
    TIMID(10, "Timid", PermanentStat.SPEED, PermanentStat.ATTACK),
    HASTY(11, "Hasty", PermanentStat.SPEED, PermanentStat.DEFENSE),
    SERIOUS(12, "Serious", null, null),
    JOLLY(13, "Jolly", PermanentStat.SPEED, PermanentStat.SPECIAL_ATTACK),
    NAIVE(14, "Naive", PermanentStat.SPEED, PermanentStat.SPECIAL_DEFENSE),
    MODEST(15, "Modest", PermanentStat.SPECIAL_ATTACK, PermanentStat.ATTACK),
    MILD(16, "Mild", PermanentStat.SPECIAL_ATTACK, PermanentStat.DEFENSE),
    QUIET(17, "Quiet", PermanentStat.SPECIAL_ATTACK, PermanentStat.SPEED),
    BASHFUL(18, "Rashful", null, null),
    RASH(19, "Rash", PermanentStat.SPECIAL_ATTACK, PermanentStat.SPECIAL_DEFENSE),
    CALM(20, "Calm", PermanentStat.SPECIAL_DEFENSE, PermanentStat.ATTACK),
    GENTLE(21, "Gentle", PermanentStat.SPECIAL_DEFENSE, PermanentStat.DEFENSE),
    SASSY(22, "Sassy", PermanentStat.SPECIAL_DEFENSE, PermanentStat.SPEED),
    CAREFUL(23, "Careful", PermanentStat.SPECIAL_DEFENSE, PermanentStat.SPECIAL_ATTACK),
    QUIRKY(24, "Quirky", null, null);

    private final Integer ID;
    private final String NAME;
    private final PermanentStat INCREASED_STAT;
    private final PermanentStat DECREASED_STAT;

    Nature(@Nonnull Integer id, @Nonnull String name, @Nullable PermanentStat increasedStat, @Nullable PermanentStat decreasedStat) {
        Holder.MAP.put(name, this);
        this.ID = id;
        this.NAME = name;
        this.INCREASED_STAT = increasedStat;
        this.DECREASED_STAT = decreasedStat;
    }

    @Nonnull
    public static Nature getNature(@Nonnull String name) {
        if (!Holder.MAP.containsKey(name)) {
            throw new NullPointerException("Nature " + " doesn't exist");
        }

        return Holder.MAP.get(name);
    }

    @Nonnull
    public Integer getID() {
        return this.ID;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public PermanentStat getIncreasedStat() {
        return this.INCREASED_STAT;
    }

    @Nullable
    public PermanentStat getDecreasedStat() {
        return this.DECREASED_STAT;
    }

    public double getNatureMultiplier(@Nonnull PermanentStat stat) {
        if (this.getIncreasedStat() != null && this.getIncreasedStat().equals(stat)) {
            return 1.1;
        } else if (this.getDecreasedStat() != null && this.getDecreasedStat().equals(stat)) {
            return 0.9;
        } else {
            return 1.0;
        }
    }

    private static class Holder {
        static Map<String, Nature> MAP = new HashMap<>();
    }

}
