package com.github.drsmugbrain.pokemon;

import org.json.JSONArray;

import javax.annotation.Nonnull;
import java.util.*;

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
                .setWeakTo(FIGHTING)
                .setImmuneTo(GHOST);

        FIRE
                .setWeakTo(WATER, GROUND, ROCK)
                .setResistantTo(FIRE, GRASS, ICE, BUG, STEEL, FAIRY);

        WATER
                .setWeakTo(ELECTRIC, GRASS)
                .setResistantTo(FIRE, WATER, ICE, STEEL);

        ELECTRIC
                .setWeakTo(GROUND)
                .setResistantTo(ELECTRIC, FLYING, STEEL);

        GRASS
                .setWeakTo(FIRE, ICE, POISON, FLYING, BUG)
                .setResistantTo(WATER, ELECTRIC, GRASS, GROUND);

        ICE
                .setWeakTo(FIRE, FIGHTING, ROCK, STEEL)
                .setResistantTo(ICE);

        FIGHTING
                .setWeakTo(FLYING, PSYCHIC, FAIRY)
                .setResistantTo(BUG, ROCK, DARK);

        POISON
                .setWeakTo(GROUND, PSYCHIC)
                .setResistantTo(GRASS, FIGHTING, POISON, BUG, FAIRY);

        GROUND
                .setWeakTo(WATER, GRASS, ICE)
                .setResistantTo(POISON, ROCK)
                .setImmuneTo(ELECTRIC);

        FLYING
                .setWeakTo(ELECTRIC, ICE, ROCK)
                .setResistantTo(GRASS, FIGHTING, BUG)
                .setImmuneTo(GROUND);

        PSYCHIC
                .setWeakTo(BUG, GHOST, DARK)
                .setResistantTo(FIGHTING, PSYCHIC);

        BUG
                .setWeakTo(FIRE, FLYING, ROCK)
                .setResistantTo(GRASS, FIGHTING, GROUND);

        ROCK
                .setWeakTo(WATER, GRASS, FIGHTING, GROUND, STEEL)
                .setResistantTo(NORMAL, FIRE, POISON, FLYING);

        GHOST
                .setWeakTo(GHOST, DARK)
                .setResistantTo(POISON, BUG)
                .setImmuneTo(NORMAL, FIGHTING);

        DRAGON
                .setWeakTo(ICE, DRAGON, FAIRY)
                .setResistantTo(FIRE, WATER, ELECTRIC, GRASS);

        DARK
                .setWeakTo(FIGHTING, BUG, FAIRY)
                .setResistantTo(GHOST, DARK)
                .setImmuneTo(PSYCHIC);

        STEEL
                .setWeakTo(FIRE, FIGHTING, GROUND)
                .setResistantTo(NORMAL, GRASS, ICE, FLYING, PSYCHIC, BUG, ROCK, DRAGON, STEEL, FAIRY)
                .setImmuneTo(POISON);

        FAIRY
                .setWeakTo(POISON, STEEL)
                .setResistantTo(FIGHTING, BUG, STEEL)
                .setImmuneTo(DRAGON);
    }

    private String NAME;
    private List<Type> WEAK_TO = new ArrayList<>();
    private List<Type> RESISTANT_TO = new ArrayList<>();
    private List<Type> IMMUNE_TO = new ArrayList<>();

    Type(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
    }

    public static double getDamageMultiplier(@Nonnull Type pokemonType, @Nonnull Type attackType) {
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

    public static double getDamageMultiplier(@Nonnull List<Type> pokemonTypes, @Nonnull Type attackType) {
        Double multiplier = 1.0;

        for (Type pokemonType : pokemonTypes) {
            multiplier *= Type.getDamageMultiplier(pokemonType, attackType);
        }

        return multiplier;
    }

    public static double getDamageMultiplier(@Nonnull Type[] pokemonTypes, @Nonnull Type attackType) {
        return Type.getDamageMultiplier(Arrays.asList(pokemonTypes), attackType);
    }

    @Nonnull
    public static Type getType(@Nonnull String type) {
        type = type.toLowerCase();
        if (!Holder.MAP.containsKey(type)) {
            throw new NullPointerException("Type " + type + " doesn't exist");
        }

        return Holder.MAP.get(type);
    }

    @Nonnull
    public static Type[] getTypes(@Nonnull String type) {
        if (!Holder.MAP.containsKey(type)) {
            throw new NullPointerException("Type " + type + " doesn't exist");
        }

        return new Type[]{Holder.MAP.get(type)};
    }

    @Nonnull
    public static Type[] getTypes(@Nonnull String firstType, @Nonnull String secondType) {
        Type type1 = Type.getType(firstType);
        Type type2 = Type.getType(secondType);
        
        return new Type[]{type1, type2};
    }

    @Nonnull
    public static Type[] getTypes(@Nonnull JSONArray jsonArray) {
        if (jsonArray.length() == 2) {
            return Type.getTypes(jsonArray.getString(0), jsonArray.getString(1));
        } else if (jsonArray.length() == 1) {
            return Type.getTypes(jsonArray.getString(0));
        } else {
            throw new IllegalArgumentException("Pokemon types must range from 1 to 2");
        }
    }

    @Nonnull
    private Type setWeakTo(@Nonnull Type... weakTo) {
        this.WEAK_TO.addAll(Arrays.asList(weakTo));
        return this;
    }

    @Nonnull
    private Type setResistantTo(@Nonnull Type... resistantTo) {
        this.RESISTANT_TO.addAll(Arrays.asList(resistantTo));
        return this;
    }

    @Nonnull
    private Type setImmuneTo(@Nonnull Type... immuneTo) {
        this.IMMUNE_TO.addAll(Arrays.asList(immuneTo));
        return this;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
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

    private static class Holder {
        static Map<String, Type> MAP = new HashMap<>();
    }

}
