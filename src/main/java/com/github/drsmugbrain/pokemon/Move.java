package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Move extends BaseMove {

    private Type type;
    private int pp;
    private double damageMultiplier = 1.0;

    protected Move(@Nonnull BaseMove baseMove) {
        super(baseMove);
        this.type = baseMove.getType();
        this.pp = baseMove.getPP();
    }

    @Nonnull
    public Type getType() {
        return this.type;
    }

    public void setType(@Nonnull Type type) {
        this.type = type;
    }

    public int getPP() {
        return this.pp;
    }

    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    protected void setDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }

    protected void incrementDamageMultiplier(double amount) {
        this.damageMultiplier += amount;
    }

    protected void decreaseDamageMultiplier(double amount) {
        this.damageMultiplier -= amount;
    }

    public static Pokemon getFirstPokemon(Pokemon pokemon1, Move move1, Pokemon pokemon2, Move move2) {
        if (move1.getPriority() > move2.getPriority()) return pokemon1;
        if (move2.getPriority() > move1.getPriority()) return pokemon2;
        if (pokemon1.getStat(Stat.SPEED) > pokemon2.getStat(Stat.SPEED)) return pokemon1;
        if (pokemon2.getStat(Stat.SPEED) > pokemon1.getStat(Stat.SPEED)) return pokemon2;

        Pokemon[] pokemons = new Pokemon[]{pokemon1, pokemon2};
        return pokemons[new Random().nextInt(pokemons.length)];
    }

}
