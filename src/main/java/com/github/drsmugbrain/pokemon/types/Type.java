package com.github.drsmugbrain.pokemon.types;

import com.github.drsmugbrain.pokemon.Action;
import com.github.drsmugbrain.pokemon.IBattle;
import com.github.drsmugbrain.pokemon.Pokemon;
import org.json.JSONArray;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 05/06/2017.
 */
public enum Type implements IBattle {

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

    Type(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
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
        type = type.toLowerCase();
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
        for (Type type : weakTo) {
            type.WEAK_BY.add(this);
        }
        return this;
    }

    @Nonnull
    private Type setResistantTo(@Nonnull Type... resistantTo) {
        this.RESISTANT_TO.addAll(Arrays.asList(resistantTo));
        for (Type type : resistantTo) {
            type.RESISTED_BY.add(this);
        }
        return this;
    }

    @Nonnull
    private Type setImmuneTo(@Nonnull Type... immuneTo) {
        this.IMMUNE_TO.addAll(Arrays.asList(immuneTo));
        for (Type type : immuneTo) {
            type.IGNORED_BY.add(this);
        }
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

    @Override
    public double damageMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
        Pokemon target = action.getTarget();
        Type attackType = action.getType();

        double modifier = 1.0;
        for (Type type : target.getTypes()) {
            if (type.getImmunities().contains(attackType)) {
                return 0.0;
            }

            if (type.getWeaknesses().contains(attackType)) {
                modifier *= 2;
            }

            if (type.getResistances().contains(attackType)) {
                modifier /= 2;
            }
        }

        return modifier;
    }

    private static class Holder {
        static Map<String, Type> MAP = new HashMap<>();
    }

}
