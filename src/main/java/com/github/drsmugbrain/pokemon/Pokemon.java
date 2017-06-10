package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon extends BasePokemon {
    private final String NICKNAME;

    private final Ability ABILITY;

    private final Nature NATURE;

    private Set<Move> MOVES;

    private final int LEVEL;

    private final Type[] TYPES;

    private final Map<Stat, Integer> INDIVIDUAL_VALUES;
    private final Map<Stat, Integer> EFFORT_VALUES;
    private final Map<Stat, Stage> STAT_STAGES;

    private double stabMultiplier = 1.5;

    private double damageMultiplier = 1;

    private boolean canSwitch = true;

    public Pokemon(@Nonnull BasePokemon basePokemon, @Nonnull Nature nature, @Nonnull Ability ability, @Nonnull Set moves, int level, @Nonnull Map<Stat, Integer> individualValues, @Nonnull Map<Stat, Integer> effortValues, Map<Stat, Stage> stat_stages) {
        super(basePokemon);

        this.NICKNAME = basePokemon.getName();

        this.ABILITY = ability;

        this.NATURE = nature;

        this.MOVES = moves;

        this.LEVEL = level;

        this.TYPES = basePokemon.getTypes();

        this.INDIVIDUAL_VALUES = individualValues;
        this.EFFORT_VALUES = effortValues;
        this.STAT_STAGES = stat_stages;
    }

    public Pokemon(@Nonnull BasePokemon basePokemon, @Nonnull Ability ability, @Nonnull Set<Move> moves, int level) {
        this(basePokemon, Nature.SERIOUS, ability, moves, level, Pokemon.getDefaultIndividualValues(), Pokemon.getDefaultEffortValues(), Pokemon.getDefaultStatStages());
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
    private static Map<Stat, Stage> getDefaultStatStages() {
        Map<Stat, Stage> statStages = new HashMap<>();

        statStages.put(Stat.HP, Stage.ZERO);
        statStages.put(Stat.ATTACK, Stage.ZERO);
        statStages.put(Stat.DEFENSE, Stage.ZERO);
        statStages.put(Stat.SPEED, Stage.ZERO);
        statStages.put(Stat.SPECIAL_ATTACK, Stage.ZERO);
        statStages.put(Stat.SPECIAL_DEFENSE, Stage.ZERO);
        statStages.put(Stat.ACCURACY, Stage.ZERO);
        statStages.put(Stat.EVASION, Stage.ZERO);

        return statStages;
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
    public Set<Move> getMoves() {
        return this.MOVES;
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
        return stat.calculate(this.getBaseStat(stat), this.getIndividualValue(stat), this.getEffortValue(stat), this.getLevel(), this.getNature().isPositiveNature(stat), this.getStatStageMultiplier(stat));
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

    public Stage getStatStage(@Nonnull Stat stat) {
        return this.STAT_STAGES.get(stat);
    }

    public double getStatStageMultiplier(@Nonnull Stat stat) {
        return this.STAT_STAGES.get(stat).getStatMultiplier(stat);
    }

    public void setStatStage(@Nonnull Stat stat, @Nonnull Stage stage) {
        this.STAT_STAGES.put(stat, stage);
    }

    protected void changeStabMultiplier(double multiplier) {
        this.stabMultiplier = multiplier;
    }

    protected void resetStabMultiplier() {
        this.stabMultiplier = 1.5;
    }

    protected void changeMoves(Set<Move> moves) {
        this.MOVES = moves;
    }

    protected void changeDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }

    protected void incrementDamageMultiplier(double amount) {
        this.damageMultiplier += amount;
    }

    protected void decreaseDamageMultiplier(double amount) {
        this.damageMultiplier -= amount;
    }

    protected void resetDamageMultiplier() {
        this.damageMultiplier = 1;
    }

    protected void setCanSwitch(boolean bool) {
        this.canSwitch = bool;
    }

}
