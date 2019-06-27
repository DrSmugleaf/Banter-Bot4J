package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.ability.Ability;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.battle.Generation;
import com.github.drsmugleaf.pokemon.battle.IModifier;
import com.github.drsmugleaf.pokemon.events.*;
import com.github.drsmugleaf.pokemon.item.Item;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.CriticalHitStage;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.moves.Moves;
import com.github.drsmugleaf.pokemon.stats.*;
import com.github.drsmugleaf.pokemon.status.Statuses;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.trainer.UserException;
import com.github.drsmugleaf.pokemon.types.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 29/09/2017.
 */
public class Pokemon {

    public final Trainer TRAINER;

    public final Species SPECIES;

    public final Types TYPES;

    public final String NICKNAME;

    public final Ability ABILITY;

    public final Nature NATURE;

    public final Item ITEM;

    public final Gender GENDER;

    private int LEVEL;

    public final Stats STATS;

    private final Map<IStat, Map<IModifier, Double>> STAT_MODIFIERS = getDefaultStatModifiers();

    private CriticalHitStage criticalHitStage = CriticalHitStage.ZERO;

    private int hp;

    public final Statuses STATUSES = new Statuses();

    public final Moves MOVES;

    @Nullable
    private Action action = null;

    @Nullable
    private Pokemon damagedThisTurn = null;

    // Kilograms
    private double weight;

    protected final List<Tag> TAGS = new ArrayList<>();

    private int toxicN = 1;

    protected Pokemon(
            Trainer trainer,
            Species species,
            String nickname,
            Abilities ability,
            Items item,
            Nature nature,
            Gender gender,
            Integer level,
            Map<PermanentStat, Integer> individualValues,
            Map<PermanentStat, Integer> effortValues,
            List<Move> moves
    ) throws UserException {
        Generation generation = trainer.BATTLE.GENERATION;
        if (generation == Generation.I || generation == Generation.II) {
            if (nature != Nature.NONE) {
                throw new UserException(nickname + " can't have a nature before generation III");
            }

            if (ability != Abilities.NONE) {
                throw new UserException(nickname + " can't have an ability before generation III");
            }
        }

        if (!species.getValidGenders().contains(gender)) {
            throw new UserException(nickname + " has an invalid gender for its species");
        }

        TRAINER = trainer;
        SPECIES = species;
        TYPES = new Types(species.getTypes());
        NICKNAME = nickname;
        ABILITY = new Ability(ability);
        ITEM = new Item(item);
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

        MOVES = new Moves(moves);
        weight = species.getWeight();
    }

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

    public String export() {
        String string = "";

        int hpEV = STATS.get(PermanentStat.HP).EV;
        int atkEV = STATS.get(PermanentStat.ATTACK).EV;
        int defEV = STATS.get(PermanentStat.DEFENSE).EV;
        int spaEV = STATS.get(PermanentStat.SPECIAL_ATTACK).EV;
        int spdEV = STATS.get(PermanentStat.SPECIAL_DEFENSE).EV;
        int speEV = STATS.get(PermanentStat.SPEED).EV;
        string = string.concat(
                String.format(
                        "%s @ %s\n" +
                        "Ability: %s\n" +
                        "EVs: %d HP / %d Atk / %d Def / %d SpA / %d SpD / %d Spe\n" +
                        "%s Nature",
                        SPECIES.NAME, ITEM.getName(), ABILITY.getName(),
                        hpEV, atkEV, defEV, spaEV, spdEV, speEV,
                        NATURE.NAME
                )
        );

        for (Move move : MOVES.get()) {
            string = string.concat(String.format("\n- %s", move.BASE_MOVE.NAME));
        }

        return string;
    }

    public Trainer getTrainer() {
        return TRAINER;
    }

    public Battle getBattle() {
        return TRAINER.BATTLE;
    }

    public Species getSpecies() {
        return SPECIES;
    }

    public String getName() {
        return SPECIES.NAME;
    }

    public String getNickname() {
        return NICKNAME;
    }

    public Nature getNature() {
        return NATURE;
    }

    public Gender getGender() {
        return GENDER;
    }

    public Integer getLevel() {
        return LEVEL;
    }

    public void setLevel(int level) {
        LEVEL = level;
    }

    public double calculate(IStat stat) {
        return STATS.get(stat).calculate(this);
    }

    public double calculateWithoutStages(IStat stat) {
        return STATS.get(stat).calculateWithoutStages(this);
    }

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

    public CriticalHitStage getCriticalHitStage() {
        return criticalHitStage;
    }

    public CriticalHitStage changeCriticalHitStage(int amount) {
        int currentStage = criticalHitStage.INDEX;
        criticalHitStage = CriticalHitStage.getStage(currentStage + amount);
        return criticalHitStage;
    }

    @Nullable
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getHP() {
        return hp;
    }

    public int getMaxHP() {
        return (int) STATS.get(PermanentStat.HP).calculate(this);
    }

    public int damage(Action action) {
        damagedThisTurn = action.getAttacker();

        int amount = action.getMove().BASE_MOVE.getDamage(action.getAttacker(), this, action);
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
            hp = 0;
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
        heal(healedHP);
    }

    protected void kill() {
        TRAINER.removeActivePokemon(this);
        Event event = new PokemonDeathEvent(this);
        EventDispatcher.dispatch(event);
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
        MOVES.resetValid();
    }

    public boolean isDamagedThisTurn() {
        return damagedThisTurn != null;
    }

    public boolean isDamagedThisTurnBy(Pokemon pokemon) {
        return damagedThisTurn == pokemon;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getStabMultiplier(Move move) {
        return 1.0;
    }

    @Nullable
    public Action getLastAction() {
        return TRAINER.BATTLE.getLastAction(this);
    }

    public List<Action> getHitBy() {
        return TRAINER.BATTLE.getHitBy(this);
    }

    @Nullable
    public Action getLastHitBy() {
        return TRAINER.BATTLE.getLastHitBy(this);
    }

    public boolean movedThisTurn() {
        return TRAINER.BATTLE.movedThisTurn(this);
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    public void retarget(Pokemon pokemon) {
        if (action != null) {
            action.setTarget(pokemon);
        }
    }

    @Nullable
    public Pokemon getEnemyOppositePokemon() {
        int index = TRAINER.getActivePokemons().indexOf(this);
        Trainer oppositeTrainer = TRAINER.BATTLE.getOppositeTrainer(TRAINER);
        Pokemon oppositePokemon = null;

        while (oppositePokemon == null && index >= 0) {
            oppositePokemon = oppositeTrainer.getActivePokemon(index);
            index--;
        }

        return oppositePokemon;
    }

    @Nullable
    public Pokemon getRandomActiveEnemyPokemon() {
        Trainer oppositeTrainer = TRAINER.BATTLE.getOppositeTrainer(TRAINER);
        return oppositeTrainer.getRandomActivePokemon();
    }

    @Nullable
    public Pokemon getRandomAdjacentEnemyPokemon() {
        return TRAINER.getRandomAdjacentEnemyPokemon(this);
    }

    public void addStatModifier(IStat stat, IModifier source, double modifier) {
        STAT_MODIFIERS.get(stat).put(source, modifier);
    }

    public void removeStatModifier(IModifier... sources) {
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

    public boolean isAlly(Pokemon pokemon) {
        return TRAINER.getActivePokemon(pokemon) > -1;
    }

    public boolean affectedBy(Action action) {
        return !TYPES.isImmune(action);
    }

}
