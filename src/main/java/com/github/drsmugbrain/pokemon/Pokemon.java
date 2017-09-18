package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.*;
import javafx.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Pokemon {

    private final Pokemons BASE_POKEMON;
    private final String NICKNAME;
    private final Ability ABILITY;
    private final Nature NATURE;
    private int LEVEL;
    private final List<Type> TYPES;
    private final Map<Stat, Integer> INDIVIDUAL_VALUES = getDefaultIndividualValues();
    private final Map<Stat, Integer> EFFORT_VALUES = getDefaultEffortValues();
    private final Map<IStat, Stage> STAT_STAGES = getDefaultStatStages();
    private int currentHP;
    private final Map<BaseVolatileStatus, VolatileStatus> VOLATILE_STATUSES = new LinkedHashMap<>();
    private final List<Pokemon> damagedThisTurnBy = new ArrayList<>();
    private final Gender GENDER;
    private final Map<Stat, Map<String, Double>> STAT_MODIFIERS = new LinkedHashMap<>();
    private Item item;
    private List<Move> MOVES;
    private List<Move> VALID_MOVES;
    private double stabMultiplier = 1.5;
    private double damageMultiplier = 1;
    private boolean canAttackThisTurn = true;
    private boolean canSwitch = true;
    private Move action = null;
    private Pokemon target = null;
    private Status status = null;
    private CriticalHitStage criticalHitStage = CriticalHitStage.ZERO;
    private boolean damagedThisTurn = false;
    private double weight;
    private boolean berryUsed = false;
    private int bideDamageTaken = 0;
    private Pokemon bideTarget = null;
    private Pair<Pokemon, Move> lastTarget = new Pair<>(null, null);
    private Battle battle = null;
    private Trainer trainer = null;
    private boolean fainted = false;
    private List<Action> hitBy = new ArrayList<>();
    private boolean movedThisTurn = false;

    public Pokemon(@Nonnull Pokemons basePokemon, @Nonnull Item item, @Nonnull Nature nature, @Nonnull Ability ability, @Nullable Gender gender, int level, @Nonnull Map<Stat, Integer> individualValues, @Nonnull Map<Stat, Integer> effortValues, @Nonnull List<Move> moves) {
        this.BASE_POKEMON = basePokemon;
        this.NICKNAME = basePokemon.getName();
        this.item = item;
        this.ABILITY = ability;
        this.NATURE = nature;
        if (gender == null) {
            this.GENDER = Gender.getRandomGender();
        } else {
            this.GENDER = gender;
        }

        this.MOVES = moves;
        this.VALID_MOVES = moves;

        this.LEVEL = level;

        this.TYPES = basePokemon.getTypes();

        this.INDIVIDUAL_VALUES.putAll(individualValues);
        this.EFFORT_VALUES.putAll(effortValues);
        this.currentHP = this.getStat(Stat.HP);
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
    private static Map<IStat, Stage> getDefaultStatStages() {
        Map<IStat, Stage> statStages = new HashMap<>();

        statStages.put(Stat.HP, Stage.ZERO);
        statStages.put(Stat.ATTACK, Stage.ZERO);
        statStages.put(Stat.DEFENSE, Stage.ZERO);
        statStages.put(Stat.SPEED, Stage.ZERO);
        statStages.put(Stat.SPECIAL_ATTACK, Stage.ZERO);
        statStages.put(Stat.SPECIAL_DEFENSE, Stage.ZERO);
        statStages.put(BattleStat.ACCURACY, Stage.ZERO);
        statStages.put(BattleStat.EVASION, Stage.ZERO);

        return statStages;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String export() {
        String string = "";

        String name = this.getName();
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

    public Pokemons getBasePokemon() {
        return this.BASE_POKEMON;
    }

    @Nonnull
    public String getName() {
        return this.BASE_POKEMON.getName();
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

    protected void setItem(@Nonnull Item item) {
        this.item = item;
    }

    public boolean hasItem() {
        return this.item != null;
    }

    public boolean hasItem(Item item) {
        return this.item.equals(item);
    }

    @Nullable
    protected Item removeItem() {
        Item item = this.item;
        this.item = null;
        return item;
    }

    protected void stealItem(Pokemon thief) {
        Item item = this.removeItem();
        thief.setItem(item);
    }

    @Nonnull
    public Nature getNature() {
        return this.NATURE;
    }

    @Nonnull
    public List<Move> getMoves() {
        return this.MOVES;
    }

    @Nonnull
    public Move getMove(BaseMove baseMove) {
        for (Move move : this.MOVES) {
            if (move.getBaseMove() == baseMove) {
                return move;
            }
        }

        throw new NullPointerException("Pokemon doesn't have move " + baseMove.getName());
    }

    @Nonnull
    public Move getMove(String moveName) {
        moveName = moveName.toLowerCase();
        for (Move move : this.MOVES) {
            if (Objects.equals(move.getBaseMove().getName().toLowerCase(), moveName)) {
                return move;
            }
        }

        throw new NullPointerException("Pokemon doesn't have move " + moveName);
    }

    protected void changeMoves(List<Move> moves) {
        this.MOVES = moves;
    }

    public boolean hasOneMove(Move... move) {
        return Collections.disjoint(this.MOVES, Arrays.asList(move));
    }

    public boolean hasOneMove(BaseMove... baseMoves) {
        List<BaseMove> baseMoveList = Arrays.asList(baseMoves);
        for (Move move : this.MOVES) {
            if (baseMoveList.contains(move.getBaseMove())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasOneMove(String moveName) {
        moveName = moveName.toLowerCase();
        for (Move move : this.MOVES) {
            if (Objects.equals(move.getBaseMove().getName().toLowerCase(), moveName)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasAllMoves(BaseMove... baseMoves) {
        return this.MOVES.containsAll(Arrays.asList(baseMoves));
    }

    @Nonnull
    public List<Move> getValidMoves() {
        return this.VALID_MOVES;
    }

    public void setValidMoves(BaseMove... baseMoves) {
        this.VALID_MOVES.clear();
        for (BaseMove baseMove : baseMoves) {
            this.VALID_MOVES.add(this.getMove(baseMove));
        }
    }

    public void setValidMoves(Move... move) {
        this.VALID_MOVES.clear();
        Collections.addAll(this.VALID_MOVES, move);
    }

    @Nonnull
    public List<Type> getTypes() {
        return this.TYPES;
    }

    protected boolean isType(Type... types) {
        for (Type type : types) {
            if (!this.TYPES.contains(type)) {
                return false;
            }
        }

        return true;
    }

    protected void setTypes(List<Type> types) {
        this.TYPES.clear();
        this.TYPES.addAll(types);
    }

    protected void setTypes(Type... types) {
        this.TYPES.clear();
        Collections.addAll(this.TYPES, types);
    }

    @Nonnull
    public String[] getTypesString() {
        List<String> types = new ArrayList<>();

        for (Type type : this.TYPES) {
            types.add(type.getName());
        }

        return types.toArray(new String[]{});
    }

    public int getLevel() {
        return this.LEVEL;
    }

    protected void setLevel(int level) {
        this.LEVEL = level;
    }

    @Nonnull
    public Map<Stat, Integer> getStats() {
        Map<Stat, Integer> stats = new LinkedHashMap<>();

        for (int i = 0; i < Stat.values().length; i++) {
            Stat stat = Stat.values()[i];
            stats.put(stat, this.getStat(stat));
        }

        return stats;
    }

    public String getStatsStringWithoutHP() {
        Map<Stat, Integer> stats = this.getStats();
        stats.remove(Stat.HP);

        return stats
                .entrySet()
                .stream()
                .map((entry) -> entry.getValue() + " " + entry.getKey().getAbbreviation())
                .collect(Collectors.joining(" / "));
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

    public int getBaseStat(@Nonnull Stat stat) {
        return this.BASE_POKEMON.getStats().get(stat);
    }

    public int getStat(@Nonnull Stat stat) {
        return stat.calculate(this, stat, this.getBattle().getGeneration(), this.getStatStageMultiplier(stat));
    }

    public int getStatWithoutStages(@Nonnull Stat stat) {
        return stat.calculateWithoutStages(this, stat, this.getBattle().getGeneration());
    }

    public int getCurrentStat(@Nonnull Stat stat) {
        if (stat == Stat.HP) {
            return this.currentHP;
        }

        return this.getStat(stat);
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

    public Map<IStat, Stage> getStatStages() {
        return this.STAT_STAGES;
    }

    public Stage getStatStage(@Nonnull IStat stat) {
        return this.STAT_STAGES.get(stat);
    }

    public double getStatStageMultiplier(@Nonnull IStat stat) {
        return this.STAT_STAGES.get(stat).getStatMultiplier(stat);
    }

    protected void setStatStage(@Nonnull IStat stat, @Nonnull Stage stage) {
        this.STAT_STAGES.put(stat, stage);
    }

    protected void setStatStage(Map<IStat, Stage> stages) {
        this.STAT_STAGES.putAll(stages);
    }

    protected void raiseStatStage(@Nonnull IStat stat, int amount) {
        this.STAT_STAGES.put(stat, Stage.getStage(this.STAT_STAGES.get(stat).getStage() + amount));
    }

    protected void lowerStatStage(@Nonnull IStat stat, int amount) {
        this.raiseStatStage(stat, -amount);
    }

    protected void resetStatStages() {
        this.STAT_STAGES.putAll(Pokemon.getDefaultStatStages());
    }

    protected double getStabMultiplier(Move move) {
        if (this.TYPES.contains(move.getType())) {
            return this.stabMultiplier;
        } else {
            return 1.0;
        }
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
        this.lastTarget = new Pair<>(target, action);
    }

    protected void resetAction() {
        this.action = null;
        this.target = null;
    }

    protected void executeTurn(Battle battle) {
        if (this.getCurrentStat(Stat.HP) <= 0) {
            return;
        }
        this.action.tryUse(this, this.target, battle, battle.getTrainer(this));
        this.movedThisTurn = true;
    }

    protected void finishTurn() {
        this.action = null;
        this.target = null;
        this.movedThisTurn = false;
        this.damagedThisTurn = false;
        this.damagedThisTurnBy.clear();
        this.canAttackThisTurn = true;
        this.VALID_MOVES = this.MOVES;
    }

    public boolean isFainted() {
        return this.fainted;
    }

    protected void kill() {
        this.fainted = true;
        this.getTrainer().removeActivePokemon(this);
        PokemonDeathEvent event = new PokemonDeathEvent(this);
        EventDispatcher.dispatch(event);
    }

    protected void damage(int amount) {
        if (currentHP - amount < 0) {
            amount = currentHP;
        }

        currentHP -= amount;
        damagedThisTurn = true;

        PokemonDamagedEvent event = new PokemonDamagedEvent(this, amount);
        EventDispatcher.dispatch(event);

        if (currentHP <= 0) {
            this.kill();
        }
    }

    protected void damage(double percentage) {
        int maxHP = this.getStat(Stat.HP);
        int damage = (int) (maxHP * (percentage / 100.0f));
        this.damage(damage);
    }

    protected int damage(Move move, Pokemon attacker) {
        int amount = move.getBaseMove().getDamage(attacker, this, move);
        hitBy.add(new Action(move, attacker, this, amount, false));
        this.damage(amount);
        return amount;
    }

    protected void heal(int amount) {
        int maxHP = this.getStat(Stat.HP);

        if (currentHP + amount > maxHP) {
            amount = maxHP - currentHP;
        }

        currentHP += amount;

        PokemonHealedEvent event = new PokemonHealedEvent(this, amount);
        EventDispatcher.dispatch(event);
    }

    protected void heal(double percentage) {
        int maxHP = this.getStat(Stat.HP);
        int healedHP = (int) (maxHP * (percentage / 100.0f));
        this.heal(healedHP);
    }

    @Nullable
    protected Status getStatus() {
        return this.status;
    }

    protected void setStatus(Status status) {
        String message = Messages.STATUS.getMessage(this, null, null, status, null, null);
        BattleMessageEvent event = new BattleMessageEvent(this.battle, message);
        EventDispatcher.dispatch(event);

        this.status = status;
    }

    protected void resetStatus() {
        this.status = null;
    }

    protected void addVolatileStatus(VolatileStatus status) {
        this.VOLATILE_STATUSES.put(status.getBaseVolatileStatus(), status);
    }

    protected void addVolatileStatus(Collection<VolatileStatus> statuses) {
        for (VolatileStatus volatileStatus : statuses) {
            this.addVolatileStatus(volatileStatus);
        }
    }

    @Nonnull
    protected Collection<VolatileStatus> getVolatileStatuses() {
        return this.VOLATILE_STATUSES.values();
    }

    protected VolatileStatus getVolatileStatus(BaseVolatileStatus status) {
        return this.VOLATILE_STATUSES.get(status);
    }

    protected boolean hasVolatileStatus(BaseVolatileStatus status) {
        return this.VOLATILE_STATUSES.containsKey(status);
    }

    protected void removeVolatileStatus(BaseVolatileStatus... statuses) {
        for (BaseVolatileStatus status : statuses) {
            this.VOLATILE_STATUSES.remove(status);
        }
    }

    protected void resetLoweredStats() {
        for (IStat stat : this.STAT_STAGES.keySet()) {
            if (this.STAT_STAGES.get(stat).getStage() < Stage.ZERO.getStage()) {
                this.STAT_STAGES.put(stat, Stage.ZERO);
            }
        }
    }

    protected CriticalHitStage getCriticalHitStage() {
        return this.criticalHitStage;
    }

    protected void setCriticalHitStage(CriticalHitStage stage) {
        this.criticalHitStage = stage;
    }

    protected void raiseCriticalHitStage(int amount) {
        this.criticalHitStage = CriticalHitStage.getStage(this.criticalHitStage.getStage() + amount);
    }

    protected void lowerCriticalHitStage(int amount) {
        this.raiseCriticalHitStage(-amount);
    }

    protected void resetCriticalHitStage() {
        this.criticalHitStage = CriticalHitStage.ZERO;
    }

    protected boolean isDamagedThisTurn() {
        return this.damagedThisTurn;
    }

    protected boolean isDamagedThisTurnBy(Pokemon pokemon) {
        return this.damagedThisTurnBy.contains(pokemon);
    }

    @Nonnull
    public Gender getGender() {
        return this.GENDER;
    }

    public double getWeight() {
        return this.weight;
    }

    protected void setWeight(int weight) {
        this.weight = weight;
    }

    protected void setWeight(double weight) {
        this.weight = (int) (this.weight * (weight / 100.0f));
    }

    protected void resetWeight() {
        this.weight = this.BASE_POKEMON.getWeight();
    }

    protected boolean isBerryUsed() {
        return this.berryUsed;
    }

    protected void setBerryUsed(boolean bool) {
        this.berryUsed = bool;
    }

    protected void resetBerryUsed() {
        this.berryUsed = false;
    }

    protected int getBideDamageTaken() {
        return this.bideDamageTaken;
    }

    protected void setBideDamageTaken(int amount) {
        this.bideDamageTaken = amount;
    }

    protected void addBideDamageTaken(int amount) {
        this.bideDamageTaken += amount;
    }

    protected void resetBideDamageTaken() {
        this.bideDamageTaken = 0;
    }

    protected Pokemon getBideTarget() {
        return this.bideTarget;
    }

    protected void setBideTarget(Pokemon pokemon) {
        this.bideTarget = pokemon;
    }

    protected Map<Stat, Map<String, Double>> getStatModifiers() {
        return this.STAT_MODIFIERS;
    }

    protected Collection<Double> getStatModifiers(Stat stat) {
        return this.STAT_MODIFIERS.get(stat).values();
    }

    protected double getStatModifier(Stat stat) {
        double total = 0;

        for (Map<String, Double> modifierMap : this.STAT_MODIFIERS.values()) {
            total += modifierMap.values().stream().mapToDouble(Double::doubleValue).sum();
        }

        return total;
    }

    protected void addStatModifier(Stat stat, String identifier, double modifier) {
        this.STAT_MODIFIERS.get(stat).put(identifier, modifier);
    }

    protected void removeStatModifier(Stat stat, String identifier) {
        this.STAT_MODIFIERS.remove(stat, identifier);
    }

    protected void removeStatModifier(String... identifiers) {
        for (String id : identifiers) {
            for (Map<String, Double> modifierMap : this.STAT_MODIFIERS.values()) {
                if (modifierMap.containsKey(id)) {
                    modifierMap.remove(id);
                }
            }
        }
    }

    protected boolean getCanAttackThisTurn() {
        return this.canAttackThisTurn;
    }

    protected void setCanAttackThisTurn(boolean bool) {
        this.canAttackThisTurn = bool;
    }

    protected void resetCanAttackThisTurn() {
        this.canAttackThisTurn = true;
    }

    @Nonnull
    protected Pair<Pokemon, Move> getLastTarget() {
        return this.lastTarget;
    }

    protected double getAccuracy() {
        return this.STAT_STAGES.get(BattleStat.ACCURACY).getAccuracyMultiplier();
    }

    protected double getEvasion() {
        return this.STAT_STAGES.get(BattleStat.EVASION).getEvasionMultiplier();
    }

    @Nullable
    public Battle getBattle() {
        return this.battle;
    }

    protected void setBattle(Battle battle) {
        this.battle = battle;
    }

    @Nullable
    public Trainer getTrainer() {
        return this.trainer;
    }

    protected void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    protected boolean isImmune(@Nonnull Move move) {
        for (Type type : this.TYPES) {
            if (type.getImmunities().contains(move.getType())) {
                return true;
            }
        }

        return false;
    }

    @Nonnull
    protected List<Action> getHitBy() {
        return this.hitBy;
    }

    @Nullable
    protected Action getLastHitBy() {
        if (hitBy.size() == 0) {
            return null;
        }

        return hitBy.get(hitBy.size() - 1);
    }

    protected boolean movedThisTurn() {
        return this.movedThisTurn;
    }

}
