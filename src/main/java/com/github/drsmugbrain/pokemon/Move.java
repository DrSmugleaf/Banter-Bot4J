package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Move implements Comparable<Move> {

    private static final Map<String, Move> BASE_MOVES = new TreeMap<>();

    private final String NAME;
    private final Category CATEGORY;
    private final int POWER;
    private final int ACCURACY;
    private final int PP;
    private final boolean IS_HIDDEN_POWER;
    private Type TYPE;
    private double damageMultiplier = 1.0;

    protected Move(@Nonnull String name, @Nonnull Type type, @Nonnull Category category, int power, int accuracy, int pp) {
        this.NAME = name;

        this.TYPE = type;

        this.CATEGORY = category;
        this.POWER = power;
        this.ACCURACY = accuracy;
        this.PP = pp;
        this.IS_HIDDEN_POWER = name.startsWith("Hidden Power ");
    }

    @Nonnull
    public static Map<String, Move> getBaseMoves() {
        return Move.BASE_MOVES;
    }

    @Override
    public int compareTo(@Nonnull Move move) {
        return this.NAME.compareTo(move.NAME);
    }

    protected void createBaseMove() {
        Move.BASE_MOVES.put(this.NAME, this);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Type getType() {
        return this.TYPE;
    }

    public void setType(@Nonnull Type type) {
        this.TYPE = type;
    }

    @Nonnull
    public Category getCategory() {
        return this.CATEGORY;
    }

    public int getPower() {
        return this.POWER;
    }

    public int getAccuracy() {
        return this.ACCURACY;
    }

    public int getPP() {
        return this.PP;
    }

    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    protected void setDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }

    protected void incrementDamageMultiplier(double multiplier) {
        this.damageMultiplier += multiplier;
    }

    protected void decreaseDamageMultiplier(double multiplier) {
        this.damageMultiplier -= multiplier;
    }

    public boolean isHiddenPower() {
        return this.IS_HIDDEN_POWER;
    }

    public int getPriority() {
        return Priority.getPriority(this);
    }

}
