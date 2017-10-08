package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class Pokemon {

    @Nonnull
    private final Trainer TRAINER;

    @Nonnull
    private final Species BASE_POKEMON;

    @Nonnull
    private final String NICKNAME;

    @Nonnull
    private final List<Type> TYPES = new ArrayList<>();

    @Nonnull
    private final Ability ABILITY;

    @Nonnull
    private final Nature NATURE;

    @Nullable
    private Item item;

    @Nonnull
    private final Gender GENDER;

    private int LEVEL;

    @Nonnull
    private final Map<Stat, Integer> INDIVIDUAL_VALUES = getDefaultIndividualValues();

    @Nonnull
    private final Map<Stat, Integer> EFFORT_VALUES = getDefaultEffortValues();

    @Nonnull
    private final Map<IStat, Stage> STAT_STAGES = getDefaultStatStages();

    @Nonnull
    private final Map<IStat, Map<IBattle, Double>> STAT_MODIFIERS = getDefaultStatModifiers();

    @Nonnull
    private CriticalHitStage criticalHitStage = CriticalHitStage.ZERO;

    private int hp = this.getStat(Stat.HP);

    @Nonnull
    private final Map<BaseVolatileStatus, VolatileStatus> VOLATILE_STATUSES = new LinkedHashMap<>();

    @Nonnull
    private final List<Move> MOVES = new ArrayList<>();

    @Nonnull
    private final List<BaseMove> VALID_MOVES = new ArrayList<>();

    @Nullable
    private Action action = null;

    @Nullable
    private Status status = null;

    private boolean abilitySuppressed = false;

    @Nullable
    private Pokemon damagedThisTurn = null;

    private double weight;

    @Nonnull
    private final List<Tag> TAGS = new ArrayList<>();

    private int toxicN = 1;

    protected Pokemon(
            @Nonnull Trainer trainer,
            @Nonnull Species basePokemon,
            @Nonnull String nickname,
            @Nonnull Ability ability,
            @Nullable Item item,
            @Nonnull Nature nature,
            @Nonnull Gender gender,
            @Nonnull Integer level,
            @Nonnull Map<Stat, Integer> individualValues,
            @Nonnull Map<Stat, Integer> effortValues,
            @Nonnull List<Move> moves
    ) {
        TRAINER = trainer;
        BASE_POKEMON = basePokemon;
        NICKNAME = nickname;
        ABILITY = ability;
        this.item = item;
        NATURE = nature;
        GENDER = gender;
        LEVEL = level;
        INDIVIDUAL_VALUES.putAll(individualValues);
        EFFORT_VALUES.putAll(effortValues);
        MOVES.addAll(moves);
        weight = basePokemon.getWeight();
    }

    @Nonnull
    private static Map<Stat, Integer> getDefaultIndividualValues() {
        Map<Stat, Integer> individualValues = new EnumMap<>(Stat.class);

        for (Stat stat : Stat.values()) {
            individualValues.put(stat, 31);
        }

        return individualValues;
    }

    @Nonnull
    private static Map<Stat, Integer> getDefaultEffortValues() {
        Map<Stat, Integer> effortValues = new EnumMap<>(Stat.class);

        for (Stat stat : Stat.values()) {
            effortValues.put(stat, 0);
        }

        return effortValues;
    }

    @Nonnull
    private static Map<IStat, Stage> getDefaultStatStages() {
        Map<IStat, Stage> statStages = new HashMap<>();

        for (Stat stat : Stat.values()) {
            statStages.put(stat, Stage.ZERO);
        }

        for (BattleStat battleStat : BattleStat.values()) {
            statStages.put(battleStat, Stage.ZERO);
        }

        return statStages;
    }

    @Nonnull
    private static Map<IStat, Map<IBattle, Double>> getDefaultStatModifiers() {
        Map<IStat, Map<IBattle, Double>> statModifiers = new HashMap<>();

        for (Stat stat : Stat.values()) {
            statModifiers.put(stat, new HashMap<>());
        }

        for (BattleStat battleStat : BattleStat.values()) {
            statModifiers.put(battleStat, new HashMap<>());
        }

        return statModifiers;
    }

    @Nonnull
    public String export() {
        String string = "";

        string = string.concat(
                String.format(
                        "%s @ %s\n" +
                        "Ability: %s\n" +
                        "EVs: %d HP / %d Atk / %d Def / %d SpA / %d SpD / %d Spe\n" +
                        "%s Nature",
                        BASE_POKEMON.getName(), item, ABILITY.getName(),
                        EFFORT_VALUES.get(Stat.HP),
                        EFFORT_VALUES.get(Stat.ATTACK),
                        EFFORT_VALUES.get(Stat.DEFENSE),
                        EFFORT_VALUES.get(Stat.SPECIAL_ATTACK),
                        EFFORT_VALUES.get(Stat.SPECIAL_DEFENSE),
                        EFFORT_VALUES.get(Stat.SPEED),
                        NATURE.getName()
                )
        );

        for (Move move : MOVES) {
            string = string.concat(String.format("\n- %s", move.getBaseMove().getName()));
        }

        return string;
    }

    @Nonnull
    public Trainer getTrainer() {
        return TRAINER;
    }

    @Nonnull
    public Battle getBattle() {
        return TRAINER.getBattle();
    }

    @Nonnull
    public Species getBasePokemon() {
        return BASE_POKEMON;
    }

    @Nonnull
    public String getName() {
        return BASE_POKEMON.getName();
    }

    @Nonnull
    public String getNickname() {
        return NICKNAME;
    }

    @Nonnull
    public List<Type> getTypes() {
        return new ArrayList<>(TYPES);
    }

    @Nonnull
    public boolean isType(@Nonnull Type... types) {
        for (Type type : types) {
            if (!TYPES.contains(type)) {
                return false;
            }
        }

        return true;
    }

    protected void setTypes(@Nonnull Collection<Type> types) {
        TYPES.clear();
        TYPES.addAll(types);
    }

    protected void setTypes(@Nonnull Type... types) {
        setTypes(Arrays.asList(types));
    }

    @Nonnull
    public Ability getAbility() {
        return ABILITY;
    }

    @Nonnull
    public Nature getNature() {
        return NATURE;
    }

    @Nullable
    public Item getItem() {
        return item;
    }

    protected void setItem(@Nonnull Item item) {
        this.item = item;
    }

    protected boolean hasItem() {
        return item != null;
    }

    protected boolean hasItem(@Nonnull Item item) {
        return this.item == item;
    }

    @Nullable
    protected Item removeItem() {
        Item item = this.item;
        this.item = null;
        return item;
    }

    protected void stealItem(@Nonnull Pokemon thief) {
        Item item = this.removeItem();

        if (item == null) {
            return;
        }

        thief.setItem(item);
    }

    @Nonnull
    public Gender getGender() {
        return GENDER;
    }

    @Nonnull
    public Integer getLevel() {
        return LEVEL;
    }

    protected void setLevel(int level) {
        LEVEL = level;
    }

    @Nonnull
    public Map<Stat, Integer> getStats() {
        Map<Stat, Integer> stats = new EnumMap<>(Stat.class);

        for (Stat stat : Stat.values()) {
            stats.put(stat, getCurrentStat(stat));
        }

        return stats;
    }

    @Nonnull
    public Integer getIndividualValue(@Nonnull Stat stat) {
        return INDIVIDUAL_VALUES.get(stat);
    }

    @Nonnull
    public Integer getEffortValue(@Nonnull Stat stat) {
        return EFFORT_VALUES.get(stat);
    }

    @Nonnull
    public Map<IStat, Stage> getStatStages() {
        return new HashMap<>(STAT_STAGES);
    }

    @Nonnull
    public Stage getStatStage(@Nonnull IStat stat) {
        return STAT_STAGES.get(stat);
    }


    protected void setStatStage(@Nonnull IStat stat, int amount) {
        Stage newStage = Stage.getStage(amount);
        STAT_STAGES.put(stat, newStage);
    }

    protected void setStatStage(@Nonnull Map<IStat, Stage> stages) {
        STAT_STAGES.putAll(stages);
    }

    protected void raiseStatStage(@Nonnull IStat stat, int amount) {
        int currentStage = STAT_STAGES.get(stat).getStage();
        Stage newStage = Stage.getStage(currentStage + amount);
        STAT_STAGES.put(stat, newStage);
    }

    protected void lowerStatStage(@Nonnull IStat stat, int amount) {
        raiseStatStage(stat, -amount);
    }

    protected void resetStatStages() {
        STAT_STAGES.putAll(getDefaultStatStages());
    }

    protected void resetLoweredStatStages() {
        for (Map.Entry<IStat, Stage> iStatStageEntry : STAT_STAGES.entrySet()) {
            IStat stat = iStatStageEntry.getKey();
            Stage stage = iStatStageEntry.getValue();

            if (stage.getStage() < Stage.ZERO.getStage()) {
                STAT_STAGES.put(stat, Stage.ZERO);
            }
        }
    }

    @Nonnull
    public Integer getBaseStat(@Nonnull Stat stat) {
        return BASE_POKEMON.getStats().get(stat);
    }

    public int getStat(@Nonnull Stat stat) {
        return stat.calculate(this, stat, TRAINER.getBattle().getGeneration(), STAT_STAGES.get(stat).getStatMultiplier(stat));
    }

    public int getStatWithoutStages(@Nonnull Stat stat) {
        return stat.calculateWithoutStages(this, stat, TRAINER.getBattle().getGeneration());
    }

    public int getCurrentStat(@Nonnull Stat stat) {
        if (stat == Stat.HP) {
            return hp;
        }

        return getStat(stat);
    }

    protected void raiseCriticalHitStage(int amount) {
        int currentStage = criticalHitStage.getStage();
        CriticalHitStage newStage = CriticalHitStage.getStage(currentStage + amount);
        criticalHitStage = newStage;
    }

    @Nullable
    public Action getAction() {
        return action;
    }

    protected void setAction(@Nonnull Action action) {
        this.action = action;
    }

    public boolean getAbilitySuppressed() {
        return abilitySuppressed;
    }

    protected void setAbilitySuppressed(boolean bool) {
        this.abilitySuppressed = bool;
    }

    protected void kill() {
        TRAINER.removeActivePokemon(this);
        Event event = new PokemonDeathEvent(this);
        EventDispatcher.dispatch(event);
    }

    protected int damage(@Nonnull Action action) {
        damagedThisTurn = action.getAttacker();

        int amount = action.getMove().getBaseMove().getDamage(action.getAttacker(), action.getTarget(), action);
        damage(amount);
        return amount;
    }

    protected void damage(int amount) {
        if (hp - amount < 0) {
            amount = hp;
        }

        hp -= amount;

        Event event = new PokemonDamagedEvent(this, amount);
        EventDispatcher.dispatch(event);

        if (hp <= 0) {
            kill();
        }
    }

    protected void damage(double percentage) {
        int maxHP = getStat(Stat.HP);
        int amount = (int) (maxHP * (percentage / 100.0d));
        damage(amount);
    }

    protected void heal(int amount) {
        int maxHP = getStat(Stat.HP);

        if (hp + amount > maxHP) {
            amount = maxHP - hp;
        }

        hp += amount;

        Event event = new PokemonHealedEvent(this, amount);
        EventDispatcher.dispatch(event);
    }

    protected void heal(double percentage) {
        int maxHP = getStat(Stat.HP);
        int healedHP = (int) (maxHP * (percentage / 100.0d));
        this.heal(healedHP);
    }

    @Nullable
    public Status getStatus() {
        return status;
    }

    protected void resetStatus() {
        status = null;
    }

    protected void setStatus(@Nonnull Status status) {
        this.status = status;
    }

    protected boolean hasAllMoves(BaseMove... moves) {
        return MOVES.containsAll(Arrays.asList(moves));
    }

    protected boolean hasOneMove(BaseMove... moves) {
        List<BaseMove> baseMoveList = Arrays.asList(moves);

        for (Move move : MOVES) {
            if (baseMoveList.contains(move));
        }

        return false;
    }

    public boolean hasOneMove(String moveName) {
        BaseMove baseMove = BaseMove.getMove(moveName);
        return hasOneMove(baseMove);
    }

    @Nonnull
    public List<Move> getMoves() {
        return new ArrayList<>(MOVES);
    }

    @Nullable
    public Move getMove(String moveName) {
        moveName = moveName.toLowerCase();

        for (Move move : MOVES) {
            if (Objects.equals(move.getBaseMove().getName(), moveName)) {
                return move;
            }
        }

        return null;
    }

    @Nonnull
    public List<BaseMove> getValidMoves() {
        return new ArrayList<>(VALID_MOVES);
    }

    protected void setValidMoves(@Nonnull BaseMove... moves) {
        VALID_MOVES.clear();
        VALID_MOVES.addAll(Arrays.asList(moves));
    }

    protected void executeTurn() {
        if (isFainted()) {
            return;
        }

        if (action != null) {
            action.tryUse();
        }
    }

    protected void finishTurn() {
        action = null;
        damagedThisTurn = null;
        VALID_MOVES.clear();
        for (Move move : MOVES) {
            VALID_MOVES.add(move.getBaseMove());
        }
    }

    protected void addVolatileStatus(@Nonnull Collection<VolatileStatus> statuses) {
        for (VolatileStatus volatileStatus : statuses) {
            VOLATILE_STATUSES.put(volatileStatus.getBaseVolatileStatus(), volatileStatus);
        }
    }

    protected void addVolatileStatus(@Nonnull VolatileStatus... statuses) {
        addVolatileStatus(Arrays.asList(statuses));
    }

    @Nullable
    protected VolatileStatus getVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return VOLATILE_STATUSES.get(status);
    }

    @Nonnull
    public Map<BaseVolatileStatus, VolatileStatus> getVolatileStatuses() {
        return new LinkedHashMap<>(VOLATILE_STATUSES);
    }

    protected boolean hasVolatileStatus(@Nonnull BaseVolatileStatus status) {
        return VOLATILE_STATUSES.containsKey(status);
    }

    protected void removeVolatileStatus(@Nonnull BaseVolatileStatus... statuses) {
        for (BaseVolatileStatus baseVolatileStatus : statuses) {
            VOLATILE_STATUSES.remove(baseVolatileStatus);
        }
    }

    protected boolean isDamagedThisTurn() {
        return damagedThisTurn != null;
    }

    protected boolean isDamagedThisTurnBy(@Nonnull Pokemon pokemon) {
        return damagedThisTurn == pokemon;
    }

    protected double getWeight() {
        return weight;
    }

    protected void setWeight(double weight) {
        this.weight = weight;
    }

    protected double getStabMultiplier(@Nonnull Move move) {
        return 1.0;
    }

    @Nullable
    protected Action getLastAction() {
        return TRAINER.getBattle().getLastAction(this);
    }

    @Nonnull
    protected List<Action> getHitBy() {
        return TRAINER.getBattle().getHitBy(this);
    }

    @Nullable
    protected Action getLastHitBy() {
        return TRAINER.getBattle().getLastHitBy(this);
    }

    protected boolean movedThisTurn() {
        return TRAINER.getBattle().movedThisTurn(this);
    }

    protected boolean isImmune(@Nonnull Move move) {
        for (Type type : TYPES) {
            if (type.getImmunities().contains(move.getType())) {
                return true;
            }
        }

        return false;
    }

    protected void addTag(@Nonnull Tag tag) {
        TAGS.add(tag);
    }

    protected boolean hasTag(@Nonnull Tag tag) {
        return TAGS.contains(tag);
    }

    protected void removeTag(@Nonnull Tag... tags) {
        TAGS.removeAll(Arrays.asList(tags));
    }

    protected double getAccuracy() {
        return STAT_STAGES.get(BattleStat.ACCURACY).getAccuracyMultiplier();
    }

    protected double getEvasion() {
        return STAT_STAGES.get(BattleStat.EVASION).getEvasionMultiplier();
    }

    protected double getEvasionWithoutStatChanges() {
        return BattleStat.EVASION.calculateWithoutStages(this, BattleStat.EVASION);
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    protected void retarget(@Nonnull Pokemon pokemon) {
        if (action != null) {
            action.setTarget(pokemon);
        }
    }

    @Nullable
    protected Pokemon getEnemyOppositePokemon() {
        int index = TRAINER.getActivePokemons().indexOf(this);
        Trainer oppositeTrainer = TRAINER.getBattle().getOppositeTrainer(TRAINER);
        Pokemon oppositePokemon = null;

        while (oppositePokemon == null && index >= 0) {
            oppositePokemon = oppositeTrainer.getActivePokemon(index);
            index--;
        }

        return oppositePokemon;
    }

    @Nullable
    protected Pokemon getRandomActiveEnemyPokemon() {
        Trainer oppositeTrainer = TRAINER.getBattle().getOppositeTrainer(TRAINER);
        return oppositeTrainer.getRandomActivePokemon();
    }

    @Nullable
    protected Pokemon getRandomAdjacentEnemyPokemon() {
        return TRAINER.getRandomAdjacentEnemyPokemon(this);
    }

    protected void addStatModifier(@Nonnull IStat stat, @Nonnull IBattle source, double modifier) {
        STAT_MODIFIERS.get(stat).put(source, modifier);
    }

    protected void removeStatModifier(@Nonnull IBattle... sources) {
        for (Map<IBattle, Double> iBattleDoubleMap : STAT_MODIFIERS.values()) {
            for (IBattle source : sources) {
                iBattleDoubleMap.remove(source);
            }
        }
    }

    protected int getToxicN() {
        return toxicN;
    }

    protected void increaseToxicN() {
        toxicN++;
    }

    protected void resetToxicN() {
        toxicN = 1;
    }

    @Nonnull
    public String[] getTypesString() {
        List<String> types = new ArrayList<>();

        for (Type type : TYPES) {
            types.add(type.getName());
        }

        return types.toArray(new String[]{});
    }

    @Nonnull
    public String getStatsStringWithoutHP() {
        Map<Stat, Integer> stats = getStats();
        stats.remove(Stat.HP);

        return stats
                .entrySet()
                .stream()
                .map((entry) -> entry.getValue() + " " + entry.getKey().getAbbreviation())
                .collect(Collectors.joining(" / "));
    }

    protected boolean isAlly(@Nonnull Pokemon pokemon) {
        return TRAINER.getActivePokemon(pokemon) > -1;
    }

}
