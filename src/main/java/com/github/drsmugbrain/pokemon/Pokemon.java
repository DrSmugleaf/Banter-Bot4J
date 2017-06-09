package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon extends BasePokemon {
    private final String NICKNAME;

    private final Ability ABILITY;

    private final Nature NATURE;

    private final int LEVEL;

    private final Type[] TYPES;

    private final Map<Stat, Integer> INDIVIDUAL_VALUES;
    private final Map<Stat, Integer> EFFORT_VALUES;

    public Pokemon(@Nonnull BasePokemon basePokemon, @Nonnull Nature nature, @Nonnull Ability ability, int level, @Nonnull Map<Stat, Integer> individualValues, @Nonnull Map<Stat, Integer> effortValues) {
        super(basePokemon);

        this.NICKNAME = basePokemon.getName();

        this.ABILITY = ability;

        this.NATURE = nature;

        this.LEVEL = level;

        this.TYPES = basePokemon.getTypes();

        this.INDIVIDUAL_VALUES = individualValues;
        this.EFFORT_VALUES = effortValues;
    }

    public Pokemon(@Nonnull BasePokemon basePokemon, @Nonnull Ability ability, int level) {
        this(basePokemon, Nature.SERIOUS, ability, level, Pokemon.getDefaultIndividualValues(), Pokemon.getDefaultEffortValues());
    }

    @Nonnull
    private static Map<Stat, Integer> getDefaultIndividualValues() {
        Map<Stat, Integer> individualValues = new HashMap<>();

        individualValues.put(Stat.HP, 31);
        individualValues.put(Stat.ATTACK, 31);
        individualValues.put(Stat.DEFENSE, 31);
        individualValues.put(Stat.SPEED, 31);
        individualValues.put(Stat.SPECIAL_ATTACK, 31);
        individualValues.put(Stat.SPECIAL_DEFENSE, 31);

        return individualValues;
    }

    @Nonnull
    private static Map<Stat, Integer> getDefaultEffortValues() {
        Map<Stat, Integer> effortValues = new HashMap<>();

        effortValues.put(Stat.HP, 0);
        effortValues.put(Stat.ATTACK, 0);
        effortValues.put(Stat.DEFENSE, 0);
        effortValues.put(Stat.SPEED, 0);
        effortValues.put(Stat.SPECIAL_ATTACK, 0);
        effortValues.put(Stat.SPECIAL_DEFENSE, 0);

        return effortValues;
    }

    @Nonnull
    public String getNickname() {
        return this.NICKNAME;
    }

    @Nonnull
    public Ability getAbility() {
        return this.ABILITY;
    }

    @Nonnull
    public Nature getNature() {
        return this.NATURE;
    }

    @Nonnull
    public Type[] getTypes() {
        return this.TYPES;
    }

    public int getLevel() {
        return this.LEVEL;
    }

    @Nonnull
    public Map<Stat, Integer> getStats() {
        Map<Stat, Integer> stats = new HashMap<>();

        for (int i = 0; i < Stat.values().length; i++) {
            Stat stat = Stat.values()[i];
            stats.put(stat, this.getStat(stat));
        }

        return stats;
    }

    public int getStat(@Nonnull Stat stat) {
        return stat.calculate(this.getBaseStat(stat), this.getIndividualValue(stat), this.getEffortValue(stat), this.getLevel(), this.getNature().isPositiveNature(stat));
    }

    @Nonnull
    public Map<Stat, Integer> getIndividualValues() {
        return this.INDIVIDUAL_VALUES;
    }

    public int getIndividualValue(@Nonnull Stat stat) {
        return this.INDIVIDUAL_VALUES.get(stat);
    }

    @Nonnull
    public Map<Stat, Integer> getEffortValues() {
        return this.EFFORT_VALUES;
    }

    public int getEffortValue(@Nonnull Stat stat) {
        return this.EFFORT_VALUES.get(stat);
    }

}
