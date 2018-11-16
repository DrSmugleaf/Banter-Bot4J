package com.github.drsmugleaf.pokemon.types;

import org.json.JSONArray;

import org.jetbrains.annotations.NotNull;
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

    @NotNull
    private String NAME;

    @NotNull
    private List<Type> WEAK_TO = new ArrayList<>();

    @NotNull
    private List<Type> RESISTANT_TO = new ArrayList<>();

    @NotNull
    private List<Type> IMMUNE_TO = new ArrayList<>();

    @NotNull
    private List<Type> WEAK_BY = new ArrayList<>();

    @NotNull
    private List<Type> RESISTED_BY = new ArrayList<>();

    @NotNull
    private List<Type> IGNORED_BY = new ArrayList<>();

    Type(@NotNull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
    }

    @NotNull
    public static Type getType(@NotNull String type) {
        type = type.toLowerCase();

        if (!Holder.MAP.containsKey(type)) {
            throw new NullPointerException("Type " + type + " doesn't exist");
        }

        return Holder.MAP.get(type);
    }

    @NotNull
    public static Type[] getTypes(@NotNull String type) {
        type = type.toLowerCase();
        if (!Holder.MAP.containsKey(type)) {
            throw new NullPointerException("Type " + type + " doesn't exist");
        }

        return new Type[]{Holder.MAP.get(type)};
    }

    @NotNull
    public static Type[] getTypes(@NotNull String firstType, @NotNull String secondType) {
        Type type1 = Type.getType(firstType);
        Type type2 = Type.getType(secondType);
        
        return new Type[]{type1, type2};
    }

    @NotNull
    public static Type[] getTypes(@NotNull JSONArray jsonArray) {
        if (jsonArray.length() == 2) {
            return Type.getTypes(jsonArray.getString(0), jsonArray.getString(1));
        } else if (jsonArray.length() == 1) {
            return Type.getTypes(jsonArray.getString(0));
        } else {
            throw new IllegalArgumentException("Pokemon types must range from 1 to 2");
        }
    }

    @NotNull
    private Type setWeakTo(@NotNull Type... weakTo) {
        WEAK_TO.addAll(Arrays.asList(weakTo));

        for (Type type : weakTo) {
            type.WEAK_BY.add(this);
        }

        return this;
    }

    @NotNull
    private Type setResistantTo(@NotNull Type... resistantTo) {
        RESISTANT_TO.addAll(Arrays.asList(resistantTo));

        for (Type type : resistantTo) {
            type.RESISTED_BY.add(this);
        }

        return this;
    }

    @NotNull
    private Type setImmuneTo(@NotNull Type... immuneTo) {
        IMMUNE_TO.addAll(Arrays.asList(immuneTo));

        for (Type type : immuneTo) {
            type.IGNORED_BY.add(this);
        }

        return this;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public List<Type> getWeaknesses() {
        return WEAK_TO;
    }

    @NotNull
    public List<Type> getResistances() {
        return RESISTANT_TO;
    }

    @NotNull
    public List<Type> getImmunities() {
        return IMMUNE_TO;
    }

    private static class Holder {
        @NotNull
        static Map<String, Type> MAP = new HashMap<>();
    }

}
