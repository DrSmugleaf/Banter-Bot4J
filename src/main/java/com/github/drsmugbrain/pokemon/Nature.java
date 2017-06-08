package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum Nature {

    HARDY(0, "Hardy", null, null),
    LONELY(1, "Lonely", Stat.ATTACK, Stat.DEFENSE),
    BRAVE(2, "Brave", Stat.ATTACK, Stat.SPEED),
    ADAMANT(3, "Adamant", Stat.ATTACK, Stat.SPECIAL_ATTACK),
    NAUGHTY(4, "Naughty", Stat.ATTACK, Stat.SPECIAL_DEFENSE),
    BOLD(5, "Bold", Stat.DEFENSE, Stat.ATTACK),
    DOCILE(6, "Docile", null, null),
    RELAXED(7, "Relaxed", Stat.DEFENSE, Stat.SPEED),
    IMPISH(8, "Impish", Stat.DEFENSE, Stat.SPECIAL_ATTACK),
    LAX(9, "Lax", Stat.DEFENSE, Stat.SPECIAL_DEFENSE),
    TIMID(10, "Timid", Stat.SPEED, Stat.ATTACK),
    HASTY(11, "Hasty", Stat.SPEED, Stat.DEFENSE),
    SERIOUS(12, "Serious", null, null),
    JOLLY(13, "Jolly", Stat.SPEED, Stat.SPECIAL_ATTACK),
    NAIVE(14, "Naive", Stat.SPEED, Stat.SPECIAL_DEFENSE),
    MODEST(15, "Modest", Stat.SPECIAL_ATTACK, Stat.ATTACK),
    MILD(16, "Mild", Stat.SPECIAL_ATTACK, Stat.DEFENSE),
    QUIET(17, "Quiet", Stat.SPECIAL_ATTACK, Stat.SPEED),
    BASHFUL(18, "Rashful", null, null),
    RASH(19, "Rash", Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENSE),
    CALM(20, "Calm", Stat.SPECIAL_DEFENSE, Stat.ATTACK),
    GENTLE(21, "Gentle", Stat.SPECIAL_DEFENSE, Stat.DEFENSE),
    SASSY(22, "Sassy", Stat.SPECIAL_DEFENSE, Stat.SPEED),
    CAREFUL(23, "Careful", Stat.SPECIAL_DEFENSE, Stat.SPECIAL_ATTACK),
    QUIRKY(24, "Quirky", null, null);

    private final Integer ID;
    private final String NAME;
    private final Stat INCREASED_STAT;
    private final Stat DECREASED_STAT;

    Nature(@Nonnull Integer id, @Nonnull String name, @Nullable Stat increasedStat, @Nullable Stat decreasedStat) {
        this.ID = id;
        this.NAME = name;
        this.INCREASED_STAT = increasedStat;
        this.DECREASED_STAT = decreasedStat;
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
    public Stat getIncreasedStat() {
        return this.INCREASED_STAT;
    }

    @Nullable
    public Stat getDecreasedStat() {
        return this.DECREASED_STAT;
    }

    @Nullable
    public Boolean isPositiveNature(Stat stat) {
        if (this.getIncreasedStat() != null && this.getIncreasedStat().equals(stat)) {
            return true;
        } else if (this.getDecreasedStat() != null && this.getDecreasedStat().equals(stat)) {
            return false;
        } else {
            return null;
        }
    }

}
