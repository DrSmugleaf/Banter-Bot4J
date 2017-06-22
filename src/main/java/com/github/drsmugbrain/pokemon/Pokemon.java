package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon extends BasePokemon {
    private final String NICKNAME;
    private Item item;
    private final Ability ABILITY;
    private final Nature NATURE;
    private final int LEVEL;
    private final Type[] TYPES;
    private final Map<Stat, Integer> INDIVIDUAL_VALUES = getDefaultIndividualValues();
    private final Map<Stat, Integer> EFFORT_VALUES = getDefaultEffortValues();
    private final Map<Stat, Stage> STAT_STAGES = getDefaultStatStages();
    private final Map<Stat, Integer> CURRENT_STATS;
    private List<Move> MOVES;
    private double stabMultiplier = 1.5;
    private double damageMultiplier = 1;
    private final Map<Move, Double> MOVE_DAMAGE_MULTIPLIER = new HashMap<Move, Double>();
    private boolean canSwitch = true;
    private Move action = null;
    private Pokemon target = null;
    private Status status = null;
    private final List<VolatileStatus> VOLATILE_STATUSES = new ArrayList<>();
    private CriticalHitStage criticalHitStage = CriticalHitStage.ZERO;

    public Pokemon(@Nonnull BasePokemon basePokemon, @Nonnull Item item, @Nonnull Nature nature, @Nonnull Ability ability, @Nonnull List<Move> moves, int level, @Nonnull Map<Stat, Integer> individualValues, @Nonnull Map<Stat, Integer> effortValues) {
        super(basePokemon);

        this.NICKNAME = basePokemon.getName();
        this.item = item;
        this.ABILITY = ability;
        this.NATURE = nature;

        this.MOVES = moves;
        for (Move move : moves) {
            this.MOVE_DAMAGE_MULTIPLIER.put(move, 1.0);
        }

        this.LEVEL = level;

        this.TYPES = basePokemon.getTypes();

        this.INDIVIDUAL_VALUES.putAll(individualValues);
        this.EFFORT_VALUES.putAll(effortValues);
        this.CURRENT_STATS = this.getStats();
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

    @Override
    public String toString() {
        String string = "";

        String name = super.getName();
        String item = this.getItem() != null ? this.getItem().getName() : null;
        String ability = this.getAbility().getName();

        string = string.concat(String.format("%s @ %s" +
                "\nAbility: %s" +
                "\nEVs: %d HP / %d Atk / %d Def / %d SpA / %d SpD / %d Spe" +
                "\n%s Nature",
                name, item, ability,
                this.getEffortValue(Stat.HP),
                this.getEffortValue(Stat.ATTACK),
                this.getEffortValue(Stat.DEFENSE),
                this.getEffortValue(Stat.SPECIAL_ATTACK),
                this.getEffortValue(Stat.SPECIAL_DEFENSE),
                this.getEffortValue(Stat.SPEED),
                this.getNature().getName()
        ));

        for (Move move : this.MOVES) {
            string = string.concat(String.format("\n- %s", move.getBaseMove().getName()));
        }

        return string;
    }

    @Nonnull
    public String getNickname() {
        return this.NICKNAME;
    }

    @Nonnull
    public Ability getAbility() {
        return this.ABILITY;
    }

    @Nullable
    public Item getItem() {
        return this.item;
    }

    public boolean hasItem(Item item) {
        return this.item.equals(item);
    }

    protected void setItem(@Nonnull Item item) {
        this.item = item;
    }

    protected void removeItem() {
        this.item = null;
    }

    @Nonnull
    public Nature getNature() {
        return this.NATURE;
    }

    @Nonnull
    public List<Move> getMoves() {
        return this.MOVES;
    }

    protected void changeMoves(List<Move> moves) {
        this.MOVES = moves;
    }

    protected boolean hasMove(Move... move) {
        return Collections.disjoint(this.MOVES, Arrays.asList(move));
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

    @Nonnull
    public Map<Stat, Integer> getCurrentStats() {
        Map<Stat, Integer> stats = new HashMap<>();

        for (int i = 0; i < Stat.values().length; i++) {
            Stat stat = Stat.values()[i];
            stats.put(stat, this.getCurrentStat(stat));
        }

        return stats;
    }

    public int getStat(@Nonnull Stat stat) {
        return stat.calculate(this, stat);
    }

    public int getCurrentStat(@Nonnull Stat stat) {
        return this.CURRENT_STATS.get(stat);
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

    protected void setStatStage(@Nonnull Stat stat, @Nonnull Stage stage) {
        this.STAT_STAGES.put(stat, stage);
    }

    protected void raiseStatStage(@Nonnull Stat stat, int amount) {
        this.STAT_STAGES.put(stat, Stage.getStage(this.STAT_STAGES.get(stat).getStage() + amount));
    }

    protected void lowerStatStage(@Nonnull Stat stat, int amount) {
        this.raiseStatStage(stat, -amount);
    }


    protected double getStabMultiplier() {
        return this.stabMultiplier;
    }

    protected void changeStabMultiplier(double multiplier) {
        this.stabMultiplier = multiplier;
    }

    protected void resetStabMultiplier() {
        this.stabMultiplier = 1.5;
    }

    protected double getDamageMultiplier() {
        return this.damageMultiplier;
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

    protected double getMoveDamageMultiplier(Move move) {
        return this.MOVE_DAMAGE_MULTIPLIER.get(move);
    }

    protected void changeMoveDamageMultiplier(Move move, double multiplier) {
        this.MOVE_DAMAGE_MULTIPLIER.put(move, multiplier);
    }

    protected void incrementMoveDamageMultiplier(Move move, double multiplier) {
        this.changeMoveDamageMultiplier(move, this.getMoveDamageMultiplier(move) + multiplier);
    }

    protected void decreaseMoveDamageMultiplier(Move move, double multiplier) {
        this.incrementMoveDamageMultiplier(move, -multiplier);
    }

    protected void resetMoveDamageMultiplier(Move move) {
        this.MOVE_DAMAGE_MULTIPLIER.put(move, 1.0);
    }

    protected void setCanSwitch(boolean bool) {
        this.canSwitch = bool;
    }

    public Move getAction() {
        return this.action;
    }

    protected Pokemon getTarget() {
        return this.target;
    }

    protected void setAction(@Nonnull Move action, @Nullable Pokemon target) {
        this.action = action;
        this.target = target;
    }

    protected void resetAction() {
        this.action = null;
        this.target = null;
    }

    protected void executeTurn() {
        this.action.getBaseMove().use(this, this.target);
    }

    protected void damage(int amount) {
        int currentHP = this.getCurrentStat(Stat.HP);
        this.CURRENT_STATS.put(Stat.HP, currentHP - amount);
    }

    protected void heal(int amount) {
        this.damage(-amount);
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    @Nullable
    protected Status getStatus() {
        return this.status;
    }

    protected void resetStatus() {
        this.status = null;
    }

    protected void addVolatileStatus(VolatileStatus status) {
        this.VOLATILE_STATUSES.add(status);
    }

    @Nonnull
    protected List<VolatileStatus> getVolatileStatuses(VolatileStatus status) {
        return this.VOLATILE_STATUSES;
    }

    protected boolean hasVolatileStatus(VolatileStatus status) {
        return this.VOLATILE_STATUSES.contains(status);
    }

    protected void removeVolatileStatus(VolatileStatus status) {
        this.VOLATILE_STATUSES.remove(status);
    }

    protected void resetLoweredStats() {
        for (Stat stat : this.STAT_STAGES.keySet()) {
            if (this.STAT_STAGES.get(stat).getStage() < Stage.ZERO.getStage()) {
                this.STAT_STAGES.put(stat, Stage.ZERO);
            }
        }
    }

    protected CriticalHitStage getCriticalHitStage() {
        return this.criticalHitStage;
    }

    protected void raiseCriticalHitStage(int amount) {
        this.criticalHitStage = CriticalHitStage.getStage(this.criticalHitStage.getStage() + amount);
    }

    protected void lowerCriticalHitStage(int amount) {
        this.raiseCriticalHitStage(-amount);
    }

    protected void setCriticalHitStage(CriticalHitStage stage) {
        this.criticalHitStage = stage;
    }

    protected void resetCriticalHitStage() {
        this.criticalHitStage = CriticalHitStage.ZERO;
    }

}
