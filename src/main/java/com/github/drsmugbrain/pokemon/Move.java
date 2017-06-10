package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Move implements Comparable<Move> {

    private static final Set<Move> BASE_MOVES = new TreeSet<>();

    private final String NAME;

    private Type TYPE;

    private final Category CATEGORY;
    private final int POWER;
    private final int ACCURACY;
    private final int PP;
    private double damageMultiplier = 1.0;
    private final boolean IS_HIDDEN_POWER;

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
    public static Set<Move> getBaseMoves() {
        return Move.BASE_MOVES;
    }

    @Override
    public int compareTo(@Nonnull Move move) {
        return this.NAME.compareTo(move.NAME);
    }

    void createBaseMove() {
        Move.BASE_MOVES.add(this);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Type getType() {
        return this.TYPE;
    }

    public void setType(Type type) {
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

    protected void decreaseDamageMultiplier(double multiplier ){
        this.damageMultiplier -= multiplier;
    }

    public boolean isHiddenPower() {
        return this.IS_HIDDEN_POWER;
    }

}
