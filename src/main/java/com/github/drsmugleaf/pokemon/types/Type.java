package com.github.drsmugleaf.pokemon.types;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;

import java.util.*;

/**
 * Created by DrSmugleaf on 05/06/2017.
 */
public enum Type {

    TYPELESS("Typeless"),
    CURSE("???"),
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
    private List<Type> WEAK_BY = new ArrayList<>();
    private List<Type> RESISTED_BY = new ArrayList<>();
    private List<Type> IGNORED_BY = new ArrayList<>();

    Type(String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
    }

    public static Type getType(String type) {
        type = type.toLowerCase();

        if (!Holder.MAP.containsKey(type)) {
            throw new NullPointerException("Type " + type + " doesn't exist");
        }

        return Holder.MAP.get(type);
    }

    public static Type[] getTypes(String type) {
        type = type.toLowerCase();
        if (!Holder.MAP.containsKey(type)) {
            throw new NullPointerException("Type " + type + " doesn't exist");
        }

        return new Type[]{Holder.MAP.get(type)};
    }

    public static Type[] getTypes(String firstType, String secondType) {
        Type type1 = Type.getType(firstType);
        Type type2 = Type.getType(secondType);
        
        return new Type[]{type1, type2};
    }

    public static Type[] getTypes(JSONArray jsonArray) {
        if (jsonArray.length() == 2) {
            return Type.getTypes(jsonArray.getString(0), jsonArray.getString(1));
        } else if (jsonArray.length() == 1) {
            return Type.getTypes(jsonArray.getString(0));
        } else {
            throw new IllegalArgumentException("Pokemon types must range from 1 to 2");
        }
    }

    @Contract("_ -> this")
    private Type setWeakTo(Type... weakTo) {
        WEAK_TO.addAll(Arrays.asList(weakTo));

        for (Type type : weakTo) {
            type.WEAK_BY.add(this);
        }

        return this;
    }

    @Contract("_ -> this")
    private Type setResistantTo(Type... resistantTo) {
        RESISTANT_TO.addAll(Arrays.asList(resistantTo));

        for (Type type : resistantTo) {
            type.RESISTED_BY.add(this);
        }

        return this;
    }

    @Contract("_ -> this")
    private Type setImmuneTo(Type... immuneTo) {
        IMMUNE_TO.addAll(Arrays.asList(immuneTo));

        for (Type type : immuneTo) {
            type.IGNORED_BY.add(this);
        }

        return this;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public List<Type> getWeaknesses() {
        return WEAK_TO;
    }

    @Contract(pure = true)
    public List<Type> getResistances() {
        return RESISTANT_TO;
    }

    @Contract(pure = true)
    public List<Type> getImmunities() {
        return IMMUNE_TO;
    }

    private static class Holder {
        static Map<String, Type> MAP = new HashMap<>();
    }

}
