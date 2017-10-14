package com.github.drsmugbrain.pokemon.pokemon;

import com.github.drsmugbrain.pokemon.IModifier;
import com.github.drsmugbrain.pokemon.Nature;
import com.github.drsmugbrain.pokemon.ability.Abilities;
import com.github.drsmugbrain.pokemon.ability.Ability;
import com.github.drsmugbrain.pokemon.battle.Battle;
import com.github.drsmugbrain.pokemon.events.*;
import com.github.drsmugbrain.pokemon.item.Item;
import com.github.drsmugbrain.pokemon.item.Items;
import com.github.drsmugbrain.pokemon.moves.Action;
import com.github.drsmugbrain.pokemon.moves.BaseMove;
import com.github.drsmugbrain.pokemon.moves.CriticalHitStage;
import com.github.drsmugbrain.pokemon.moves.Move;
import com.github.drsmugbrain.pokemon.stats.*;
import com.github.drsmugbrain.pokemon.status.Statuses;
import com.github.drsmugbrain.pokemon.trainer.Trainer;
import com.github.drsmugbrain.pokemon.types.Types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class Pokemon {

    @Nonnull
    private final Trainer TRAINER;

    @Nonnull
    private final Species BASE_POKEMON;

    @Nonnull
    public final Types TYPES;

    @Nonnull
    private final String NICKNAME;

    @Nonnull
    public final Ability ABILITY;

    @Nonnull
    private final Nature NATURE;

    @Nonnull
    public final Item ITEM;

    @Nonnull
    private final Gender GENDER;

    private int LEVEL;

    @Nonnull
    public final Stats STATS;

    @Nonnull
    private final Map<IStat, Map<IModifier, Double>> STAT_MODIFIERS = getDefaultStatModifiers();

    @Nonnull
    private CriticalHitStage criticalHitStage = CriticalHitStage.ZERO;

    private int hp;

    @Nonnull
    public final Statuses STATUSES = new Statuses();

    @Nonnull
    private final List<Move> MOVES = new ArrayList<>();

    @Nonnull
    private final List<BaseMove> VALID_MOVES = new ArrayList<>();

    @Nullable
    private Action action = null;

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
            @Nonnull Abilities ability,
            @Nullable Items item,
            @Nonnull Nature nature,
            @Nonnull Gender gender,
            @Nonnull Integer level,
            @Nonnull Map<PermanentStat, Integer> individualValues,
            @Nonnull Map<PermanentStat, Integer> effortValues,
            @Nonnull List<Move> moves
    ) {
        TRAINER = trainer;
        BASE_POKEMON = basePokemon;
        TYPES = new Types(basePokemon.getTypes());
        NICKNAME = nickname;
        ABILITY = new Ability(ability);
        this.ITEM = new Item(item);
        NATURE = nature;
        GENDER = gender;
        LEVEL = level;

        StatBuilder builder = new StatBuilder();
        for (Map.Entry<PermanentStat, Integer> entry : individualValues.entrySet()) {
            IStat stat = entry.getKey();
            int iv = entry.getValue();

            builder.setIV(stat, iv);
        }
        for (Map.Entry<PermanentStat, Integer> entry : effortValues.entrySet()) {
            IStat stat = entry.getKey();
            int ev = entry.getValue();

            builder.setEV(stat, ev);
        }
        STATS = new Stats(builder);

        hp = (int) PermanentStat.HP.calculate(this);

        MOVES.addAll(moves);
        weight = basePokemon.getWeight();
    }

    @Nonnull
    private static Map<IStat, Map<IModifier, Double>> getDefaultStatModifiers() {
        Map<IStat, Map<IModifier, Double>> statModifiers = new HashMap<>();

        for (PermanentStat stat : PermanentStat.values()) {
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

        int hpEV = STATS.get(PermanentStat.HP).getEV();
        int atkEV = STATS.get(PermanentStat.ATTACK).getEV();
        int defEV = STATS.get(PermanentStat.DEFENSE).getEV();
        int spaEV = STATS.get(PermanentStat.SPECIAL_ATTACK).getEV();
        int spdEV = STATS.get(PermanentStat.SPECIAL_DEFENSE).getEV();
        int speEV = STATS.get(PermanentStat.SPEED).getEV();
        string = string.concat(
                String.format(
                        "%s @ %s\n" +
                        "Ability: %s\n" +
                        "EVs: %d HP / %d Atk / %d Def / %d SpA / %d SpD / %d Spe\n" +
                        "%s Nature",
                        BASE_POKEMON.getName(), ITEM.getName(), ABILITY.getName(),
                        hpEV, atkEV, defEV, spaEV, spdEV, speEV,
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
    public Nature getNature() {
        return NATURE;
    }

    @Nonnull
    public Gender getGender() {
        return GENDER;
    }

    @Nonnull
    public Integer getLevel() {
        return LEVEL;
    }

    public void setLevel(int level) {
        LEVEL = level;
    }

    public double calculate(@Nonnull IStat stat) {
        return STATS.get(stat).calculate(this);
    }

    public double calculateWithoutStages(@Nonnull IStat stat) {
        return STATS.get(stat).calculateWithoutStages(this);
    }

    @Nonnull
    public String getStatsStringWithoutHP() {
        StringBuilder builder = new StringBuilder();
        PermanentStat[] stats = PermanentStat.values();
        for (int i = 0; i < stats.length; i++) {
            PermanentStat stat = stats[i];
            if (stat == PermanentStat.HP) {
                continue;
            }

            int value = (int) STATS.get(stat).calculate(this);
            builder
                    .append(value)
                    .append(" ")
                    .append(stat.getAbbreviation());

            if (i != stats.length - 1) {
                builder.append((" / "));
            }
        }

        return builder.toString();
    }

    public void raiseCriticalHitStage(int amount) {
        int currentStage = criticalHitStage.getStage();
        CriticalHitStage newStage = CriticalHitStage.getStage(currentStage + amount);
        criticalHitStage = newStage;
    }

    @Nullable
    public Action getAction() {
        return action;
    }

    public void setAction(@Nonnull Action action) {
        this.action = action;
    }

    public int getHP() {
        return hp;
    }

    public int getMaxHP() {
        return (int) STATS.get(PermanentStat.HP).calculate(this);
    }

    public int damage(@Nonnull Action action) {
        damagedThisTurn = action.getAttacker();

        int amount = action.getMove().getBaseMove().getDamage(action.getAttacker(), action.getTarget(), action);
        damage(amount);
        return amount;
    }

    public void damage(int amount) {
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

    public void damage(double percentage) {
        int maxHP = getMaxHP();
        int amount = (int) (maxHP * (percentage / 100.0d));
        damage(amount);
    }

    public void heal(int amount) {
        int maxHP = getMaxHP();

        if (hp + amount > maxHP) {
            amount = maxHP - hp;
        }

        hp += amount;

        Event event = new PokemonHealedEvent(this, amount);
        EventDispatcher.dispatch(event);
    }

    public void heal(double percentage) {
        int maxHP = getMaxHP();
        int healedHP = (int) (maxHP * (percentage / 100.0d));
        this.heal(healedHP);
    }

    protected void kill() {
        TRAINER.removeActivePokemon(this);
        Event event = new PokemonDeathEvent(this);
        EventDispatcher.dispatch(event);
    }

    public boolean hasAllMoves(BaseMove... moves) {
        return MOVES.containsAll(Arrays.asList(moves));
    }

    public boolean hasOneMove(BaseMove... moves) {
        List<BaseMove> baseMoveList = Arrays.asList(moves);

        for (Move move : MOVES) {
            if (baseMoveList.contains(move.getBaseMove())) {
                return true;
            }
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

    public void setValidMoves(@Nonnull BaseMove... moves) {
        VALID_MOVES.clear();
        VALID_MOVES.addAll(Arrays.asList(moves));
    }

    public void executeTurn() {
        if (isFainted()) {
            return;
        }

        if (action != null) {
            action.tryUse();
        }
    }

    public void finishTurn() {
        action = null;
        damagedThisTurn = null;
        VALID_MOVES.clear();
        for (Move move : MOVES) {
            VALID_MOVES.add(move.getBaseMove());
        }
    }

    public boolean isDamagedThisTurn() {
        return damagedThisTurn != null;
    }

    public boolean isDamagedThisTurnBy(@Nonnull Pokemon pokemon) {
        return damagedThisTurn == pokemon;
    }

    protected double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getStabMultiplier(@Nonnull Move move) {
        return 1.0;
    }

    @Nullable
    public Action getLastAction() {
        return TRAINER.getBattle().getLastAction(this);
    }

    @Nonnull
    public List<Action> getHitBy() {
        return TRAINER.getBattle().getHitBy(this);
    }

    @Nullable
    public Action getLastHitBy() {
        return TRAINER.getBattle().getLastHitBy(this);
    }

    public boolean movedThisTurn() {
        return TRAINER.getBattle().movedThisTurn(this);
    }

    public void addTag(@Nonnull Tag tag) {
        TAGS.add(tag);
    }

    public boolean hasTag(@Nonnull Tag tag) {
        return TAGS.contains(tag);
    }

    protected void removeTag(@Nonnull Tag... tags) {
        TAGS.removeAll(Arrays.asList(tags));
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    public void retarget(@Nonnull Pokemon pokemon) {
        if (action != null) {
            action.setTarget(pokemon);
        }
    }

    @Nullable
    public Pokemon getEnemyOppositePokemon() {
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
    public Pokemon getRandomActiveEnemyPokemon() {
        Trainer oppositeTrainer = TRAINER.getBattle().getOppositeTrainer(TRAINER);
        return oppositeTrainer.getRandomActivePokemon();
    }

    @Nullable
    public Pokemon getRandomAdjacentEnemyPokemon() {
        return TRAINER.getRandomAdjacentEnemyPokemon(this);
    }

    public void addStatModifier(@Nonnull IStat stat, @Nonnull IModifier source, double modifier) {
        STAT_MODIFIERS.get(stat).put(source, modifier);
    }

    public void removeStatModifier(@Nonnull IModifier... sources) {
        for (Map<IModifier, Double> iBattleDoubleMap : STAT_MODIFIERS.values()) {
            for (IModifier source : sources) {
                iBattleDoubleMap.remove(source);
            }
        }
    }

    public int getToxicN() {
        return toxicN;
    }

    public void increaseToxicN() {
        toxicN++;
    }

    public void resetToxicN() {
        toxicN = 1;
    }

    protected boolean isAlly(@Nonnull Pokemon pokemon) {
        return TRAINER.getActivePokemon(pokemon) > -1;
    }

}
