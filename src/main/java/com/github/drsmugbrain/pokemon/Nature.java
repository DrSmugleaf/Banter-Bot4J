package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Nature {

    private final Integer ID;
    private final String NAME;
    private final Stat INCREASED_STAT;
    private final Stat DECREASED_STAT;

    public static final Nature HARDY = new Nature(0, "Hardy", null, null);
    public static final Nature LONELY = new Nature(1, "Lonely", Stat.ATTACK, Stat.DEFENSE);
    public static final Nature BRAVE = new Nature(2, "Brave", Stat.ATTACK, Stat.SPEED);
    public static final Nature ADAMANT = new Nature(3, "Adamant", Stat.ATTACK, Stat.SPECIAL_ATTACK);
    public static final Nature NAUGHTY = new Nature(4, "Naughty", Stat.ATTACK, Stat.SPECIAL_DEFENSE);
    public static final Nature BOLD = new Nature(5, "Bold", Stat.DEFENSE, Stat.ATTACK);
    public static final Nature DOCILE = new Nature(6, "Docile", null, null);
    public static final Nature RELAXED = new Nature(7, "Relaxed", Stat.DEFENSE, Stat.SPEED);
    public static final Nature IMPISH = new Nature(8, "Impish", Stat.DEFENSE, Stat.SPECIAL_ATTACK);
    public static final Nature LAX = new Nature(9, "Lax", Stat.DEFENSE, Stat.SPECIAL_DEFENSE);
    public static final Nature TIMID = new Nature(10, "Timid", Stat.SPEED, Stat.ATTACK);
    public static final Nature HASTY = new Nature(11, "Hasty", Stat.SPEED, Stat.DEFENSE);
    public static final Nature SERIOUS = new Nature(12, "Serious", null, null);
    public static final Nature JOLLY = new Nature(13, "Jolly", Stat.SPEED, Stat.SPECIAL_ATTACK);
    public static final Nature NAIVE = new Nature(14, "Naive", Stat.SPEED, Stat.SPECIAL_DEFENSE);
    public static final Nature MODEST = new Nature(15, "Modest", Stat.SPECIAL_ATTACK, Stat.ATTACK);
    public static final Nature MILD = new Nature(16, "Mild", Stat.SPECIAL_ATTACK, Stat.DEFENSE);
    public static final Nature QUIET = new Nature(17, "Quiet", Stat.SPECIAL_ATTACK, Stat.SPEED);
    public static final Nature BASHFUL = new Nature(18, "Bashful", null, null);
    public static final Nature RASH = new Nature(19, "Rash", Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENSE);
    public static final Nature CALM = new Nature(20, "Calm", Stat.SPECIAL_DEFENSE, Stat.ATTACK);
    public static final Nature GENTLE = new Nature(21, "Gentle", Stat.SPECIAL_DEFENSE, Stat.DEFENSE);
    public static final Nature SASSY = new Nature(22, "Sassy", Stat.SPECIAL_DEFENSE, Stat.SPEED);
    public static final Nature CAREFUL = new Nature(23, "Careful", Stat.SPECIAL_DEFENSE, Stat.SPECIAL_ATTACK);
    public static final Nature QUIRKY = new Nature(24, "Quirky", null, null);

    private Nature(@Nonnull Integer id, @Nonnull String name, @Nullable Stat increasedStat, @Nullable Stat decreasedStat) {
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

}
