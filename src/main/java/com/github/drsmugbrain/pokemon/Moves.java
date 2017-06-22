package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 16/06/2017.
 */
public class Moves {

    public static final Map<String, Moves> BASE_MOVES = new TreeMap<>();

    private final String NAME;
    private final Type TYPE;
    private final Category CATEGORY;
    private final int PP;
    private final int POWER;
    private final int ACCURACY;
    private final String BATTLE_EFFECT;
    private final String SECONDARY_EFFECT;
    private final Integer EFFECT_RATE;
    private final double BASE_CRITICAL_HIT_RATE;
    private final int PRIORITY;
//    private final POSITION POKEMONS_HIT;
    private final boolean PHYSICAL_CONTACT;
    private final boolean SOUND_TYPE;
    private final boolean PUNCH_MOVE;
    private final boolean SNATCHABLE;
    private final boolean Z_MOVE;
    private final String CORRESPONDING_Z_MOVE;
    private final Integer Z_MOVE_POWER;
    private final Item Z_MOVE_ITEM;
    private final boolean DEFROSTS_WHEN_USED;
    private final boolean HITS_OPPOSITE_SIDE_IN_TRIPLES;
    private final boolean REFLECTED;
    private final boolean BLOCKED;
    private final boolean COPYABLE;
    private final Map<BaseMove, Map<Category, Integer>> MOVES_THAT_TURN_INTO_THIS = new HashMap<>();

    Moves(@Nonnull String name, @Nonnull Type type, @Nonnull Category category, int pp, int power, int accuracy, @Nonnull String battleEffect, @Nonnull String secondaryEffect, @Nullable Integer effectRate, double baseCriticalHitRate, int priority, boolean physicalContact, boolean soundType, boolean punchMove, boolean snatchable, boolean zMove, @Nullable String correspondingZMove, @Nullable Integer zMovePower, @Nullable Item zMoveItem, boolean defrostsWhenUsed, boolean hitsOppositeSideInTriples, boolean reflected, boolean blocked, boolean copyable, @Nullable Map<BaseMove, Map<Category, Integer>> movesThatTurnIntoThis) {
        this.NAME = name;
        this.TYPE = type;
        this.CATEGORY = category;
        this.PP = pp;
        this.POWER = power;
        this.ACCURACY = accuracy;
        this.BATTLE_EFFECT = battleEffect;
        this.SECONDARY_EFFECT = secondaryEffect;
        this.EFFECT_RATE = effectRate;
        this.BASE_CRITICAL_HIT_RATE = baseCriticalHitRate;
        this.PRIORITY = priority;
//        this.POKEMONS_HIT = pokemonsHit;
        this.PHYSICAL_CONTACT = physicalContact;
        this.SOUND_TYPE = soundType;
        this.PUNCH_MOVE = punchMove;
        this.SNATCHABLE = snatchable;
        this.Z_MOVE = zMove;
        this.CORRESPONDING_Z_MOVE = correspondingZMove;
        this.Z_MOVE_POWER = zMovePower;
        this.Z_MOVE_ITEM = zMoveItem;
        this.DEFROSTS_WHEN_USED = defrostsWhenUsed;
        this.HITS_OPPOSITE_SIDE_IN_TRIPLES = hitsOppositeSideInTriples;
        this.REFLECTED = reflected;
        this.BLOCKED = blocked;
        this.COPYABLE = copyable;
        if (movesThatTurnIntoThis != null) {
            this.MOVES_THAT_TURN_INTO_THIS.putAll(movesThatTurnIntoThis);
        }
    }

    public static Moves getBaseMove(@Nonnull String name) {
        return Moves.BASE_MOVES.get(name);
    }

    public static void toMove() {

    }

    public static void toCSV() {

    }

    public void createBaseMove() {
        Moves.BASE_MOVES.put(this.NAME, this);
    }

}
