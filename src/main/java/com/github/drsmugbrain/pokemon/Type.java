package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DrSmugleaf on 05/06/2017.
 */
public enum Type {

    NORMAL("Normal"),
    FIRE("Fire"),
    WATER("Water"),
    ELECTRIC("Electric"),
    GRASS("Grass"),
    ICE("Ice"),
    FIGHTING("Fighting"),
    POISON("Poison"),
    GROUND("Ground"),
    FLYING("Flying"),
    PSYCHIC("Psychic"),
    BUG("Bug"),
    ROCK("Rock"),
    GHOST("Ghost"),
    DRAGON("Dragon"),
    DARK("Dark"),
    STEEL("Steel"),
    FAIRY("Fairy");

    static {
        NORMAL
                .setWeakTo(Type.FIGHTING)
                .setImmuneTo(Type.GHOST);

        FIRE
                .setWeakTo(Type.WATER, Type.GROUND, Type.ROCK)
                .setImmuneTo(Type.FIRE, Type.GRASS, Type.ICE, Type.BUG, Type.STEEL, Type.FAIRY);

        WATER
                .setWeakTo(Type.ELECTRIC, Type.GRASS)
                .setResistantTo(Type.FIRE, Type.WATER, Type.ICE, Type.STEEL);

        ELECTRIC
                .setWeakTo(Type.GROUND)
                .setResistantTo(Type.ELECTRIC, Type.FLYING, Type.STEEL);

        GRASS
                .setWeakTo(Type.FIRE, Type.ICE, Type.POISON, Type.FLYING, Type.BUG)
                .setResistantTo(Type.WATER, Type.ELECTRIC, Type.GRASS, Type.GROUND);

        ICE
                .setWeakTo(Type.FIRE, Type.FIGHTING, Type.ROCK, Type.STEEL)
                .setResistantTo(Type.ICE);

        FIGHTING
                .setWeakTo(Type.FLYING, Type.PSYCHIC, Type.FAIRY)
                .setResistantTo(Type.BUG, Type.ROCK, Type.DARK);

        POISON
                .setWeakTo(Type.GROUND, Type.PSYCHIC)
                .setResistantTo(Type.GRASS, Type.FIGHTING, Type.PSYCHIC, Type.BUG, Type.FAIRY);

        GROUND
                .setWeakTo(Type.WATER, Type.GRASS, Type.ICE)
                .setResistantTo(Type.POISON, Type.ROCK)
                .setImmuneTo(Type.ELECTRIC);

        FLYING
                .setWeakTo(Type.ELECTRIC, Type.ICE, Type.ROCK)
                .setResistantTo(Type.GRASS, Type.FIGHTING, Type.BUG)
                .setImmuneTo(Type.GROUND);

        PSYCHIC
                .setWeakTo(Type.BUG, Type.GHOST, Type.DARK)
                .setResistantTo(Type.FIGHTING, Type.PSYCHIC);

        BUG
                .setWeakTo(Type.FIRE, Type.FLYING, Type.ROCK)
                .setResistantTo();

        ROCK
                .setWeakTo(Type.WATER, Type.GRASS, Type.FIGHTING, Type.GROUND)
                .setResistantTo(Type.NORMAL, Type.FIRE, Type.POISON, Type.FLYING);

        GHOST
                .setWeakTo(Type.GHOST, Type.DARK)
                .setResistantTo(Type.POISON, Type.BUG)
                .setImmuneTo(Type.NORMAL, Type.FIGHTING);

        DRAGON
                .setWeakTo(Type.ICE, Type.DRAGON, Type.FAIRY)
                .setResistantTo(Type.FIRE, Type.WATER, Type.ELECTRIC, Type.GRASS);

        DARK
                .setWeakTo(Type.FIGHTING, Type.BUG, Type.FAIRY)
                .setResistantTo(Type.GHOST, Type.DARK)
                .setImmuneTo(Type.PSYCHIC);

        STEEL
                .setWeakTo(Type.FIRE, Type.FIGHTING, Type.GROUND)
                .setResistantTo(Type.NORMAL, Type.GRASS, Type.ICE, Type.FLYING, Type.PSYCHIC, Type.BUG, Type.ROCK, Type.DRAGON, Type.STEEL, Type.FAIRY);

        FAIRY
                .setWeakTo(Type.POISON, Type.STEEL)
                .setResistantTo(Type.FIGHTING, Type.BUG, Type.STEEL)
                .setImmuneTo(Type.DRAGON);
    }

    private String NAME;
    private List<Type> WEAK_TO = new ArrayList<>();
    private List<Type> RESISTANT_TO = new ArrayList<>();
    private List<Type> IMMUNE_TO = new ArrayList<>();

    Type(@Nonnull String name) {
        this.NAME = name;
    }

    private Type setWeakTo(@Nonnull Type... weakTo) {
        this.WEAK_TO.addAll(Arrays.asList(weakTo));
        return this;
    }

    private Type setResistantTo(@Nonnull Type... resistantTo) {
        this.RESISTANT_TO.addAll(Arrays.asList(resistantTo));
        return this;
    }

    private Type setImmuneTo(@Nonnull Type... immuneTo) {
        this.IMMUNE_TO.addAll(Arrays.asList(immuneTo));
        return this;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public static Double getDamageMultiplier(List<Type> pokemonTypes, Type attackType) {
        Double multiplier = 1.0;

        for (Type pokemonType : pokemonTypes) {
            multiplier *= Type.getDamageMultiplier(pokemonType, attackType);
        }

        return multiplier;
    }

    @Nonnull
    public static Double getDamageMultiplier(Type pokemonType, Type attackType) {
        if (pokemonType.getImmunities().contains(attackType)) {
            return 0.0;
        }

        if(pokemonType.getWeaknesses().contains(attackType)) {
            return 2.0;
        }

        if(pokemonType.getResistances().contains(attackType)) {
            return 0.5;
        }

        return 1.0;
    }

    @Nonnull
    public List<Type> getWeaknesses() {
        return this.WEAK_TO;
    }

    @Nonnull
    public List<Type> getResistances() {
        return this.RESISTANT_TO;
    }

    @Nonnull
    public List<Type> getImmunities() {
        return this.IMMUNE_TO;
    }

}
