package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.events.*;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.trainer.TrainerBuilder;
import com.github.drsmugleaf.pokemon.trainer.TrainerStatus;
import com.github.drsmugleaf.pokemon.trainer.UserException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Battle extends Setup {

    private final Map<Long, Trainer> TRAINERS = new LinkedHashMap<>();
    private Weather weather;
    private final List<Turn> TURNS = new ArrayList<>();

    protected Battle(@Nonnull Setup setup, @Nonnull List<TrainerBuilder> trainers) throws UserException {
        super(setup);

        TURNS.add(new Turn(this));

        for (TrainerBuilder trainerBuilder : trainers) {
            trainerBuilder.setBattle(this);
            Trainer trainer = trainerBuilder.build();
            TRAINERS.put(trainer.getID(), trainer);
            trainer.setStatus(TrainerStatus.CHOOSING_POKEMON);
        }

        Event battleEvent = new BattleStartedEvent(this);
        EventDispatcher.dispatch(battleEvent);

        Event trainersEvent = new TrainerChoosingPokemonEvent(TRAINERS.values());
        EventDispatcher.dispatch(trainersEvent);
    }

    @Nullable
    public Action getAction(@Nonnull Pokemon attacker) {
        return attacker.getAction();
    }

    @Nonnull
    public Map<Long, Trainer> getTrainers() {
        return new HashMap<>(TRAINERS);
    }

    @Nullable
    public Trainer getTrainer(Long id) {
        return TRAINERS.get(id);
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    protected void removeWeather(@Nonnull Weather weather) {
        if (this.weather == weather) {
            this.weather = null;
        }
    }

    public void sendOut(Trainer trainer, Pokemon pokemon) {
        getCurrentTurn().sendOut(trainer, pokemon);
        trainer.sendOut(pokemon);

        for (Trainer trainer1 : TRAINERS.values()) {
            if (trainer1.getStatus() == TrainerStatus.CHOOSING_POKEMON) {
                return;
            }
        }

        for (Pokemon pokemon1 : getCurrentTurn().getPokemonsSentOut().values()) {
            Event event = new TrainerSendOutPokemonEvent(pokemon1);
            EventDispatcher.dispatch(event);
        }

        for (Trainer trainer1 : TRAINERS.values()) {
            trainer1.setPokemonInFocus(trainer1.getActivePokemons().get(0));
            trainer1.setStatus(TrainerStatus.CHOOSING_MOVE);
        }

        Event event = new BattleTurnStartEvent(this);
        EventDispatcher.dispatch(event);
    }

    public void addAction(Trainer trainer, Pokemon pokemon, Move move, Pokemon target) {
        trainer.addAction(this, move, target, pokemon);
        for (Trainer trainer1 : TRAINERS.values()) {
            if (trainer1.getStatus() != TrainerStatus.WAITING) {
                return;
            }
        }

        executeTurn();
    }

    public void executeTurn() {
        getCurrentTurn().execute(TRAINERS.values());
        nextTurn();
    }

    public void nextTurn() {
        TURNS.add(new Turn(this));

        for (Trainer trainer : TRAINERS.values()) {
            trainer.finishTurn();
        }

        if (TRAINERS.values().stream().allMatch(trainer -> trainer.getStatus() != TrainerStatus.CHOOSING_POKEMON)) {
            Event event = new BattleTurnStartEvent(this);
            EventDispatcher.dispatch(event);
        }

    }

    @Nonnull
    public List<Pokemon> getTargetList(@Nonnull Pokemon pokemon) {
        List<Pokemon> targets = new ArrayList<>();

        for (Trainer trainer : TRAINERS.values()) {
            targets.addAll(trainer.getActivePokemons());
        }

        return targets;
    }

    public int getTurn() {
        if (TURNS.isEmpty()) {
            return 0;
        }
        return getCurrentTurn().getID();
    }

    @Nonnull
    public Turn getCurrentTurn() {
        return TURNS.get(TURNS.size() - 1);
    }

    @Nonnull
    public Action createAction(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon target) {
        return new Action(move, attacker, target, getCurrentTurn().getID());
    }

    @Nonnull
    public List<Action> getAllActions() {
        List<Action> actions = new ArrayList<>();

        for (Turn turn : TURNS) {
            actions.addAll(turn.getTurnOrder());
        }

        return actions;
    }

    @Nonnull
    public List<Action> getAllActionsReverse() {
        List<Action> actions = new ArrayList<>();

        for (int i = TURNS.size() - 1; i >= 0; i--) {
            Turn turn = TURNS.get(i);
            List<Action> turnOrder = turn.getTurnOrder();
            for (int j = turnOrder.size() - 1; j >= 0; j--) {
                actions.add(turnOrder.get(j));
            }
        }

        return actions;
    }

    @Nullable
    public Action getLastAction() {
        for (int i = TURNS.size() - 1; i >= 0; i--) {
            Turn turn = TURNS.get(i);
            List<Action> turnOrder = turn.getTurnOrder();
            if (!turnOrder.isEmpty()) {
                return turnOrder.get(turnOrder.size() - 1);
            }
        }

        return null;
    }

    @Nullable
    public Action getLastAction(@Nonnull Pokemon pokemon) {
        for (int i = TURNS.size() - 1; i >= 0; i--) {
            Turn turn = TURNS.get(i);
            List<Action> turnOrder = turn.getTurnOrder();
            if (!turnOrder.isEmpty()) {
                for (Action action : turnOrder) {
                    if (action.getAttacker() == pokemon) {
                        return action;
                    }
                }
            }
        }

        return null;
    }

    public Action replaceAction(@Nonnull Action oldAction, @Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon target) {
        return new Action(move, attacker, target, getCurrentTurn().getID());
    }


    @Nonnull
    public Trainer getOppositeTrainer(@Nonnull Trainer trainer) {
        List<Trainer> trainers = new ArrayList<>(TRAINERS.values());
        trainers.remove(trainer);
        return trainers.get(0);
    }

    @Nonnull
    public List<Action> getHitBy(@Nonnull Pokemon pokemon) {
        List<Action> hitBy = new ArrayList<>();

        for (Turn turn : TURNS) {
            hitBy.addAll(turn.getHitBy(pokemon));
        }

        return hitBy;
    }

    @Nullable
    public Action getLastHitBy(@Nonnull Pokemon pokemon) {
        List<Action> hitBy = getHitBy(pokemon);

        if (hitBy.isEmpty()) {
            return null;
        }

        return hitBy.get(hitBy.size() - 1);
    }

    public boolean wasHitByThisTurn(@Nonnull Pokemon pokemon, @Nonnull BaseMove move) {
        List<Action> hitBy = getCurrentTurn().getHitBy(pokemon);

        for (Action action : hitBy) {
            if (action.getBaseMove() == move) {
                return true;
            }
        }

        return false;
    }

    public boolean movedThisTurn(@Nonnull Pokemon pokemon) {
        for (Action action : getCurrentTurn().getTurnOrder()) {
            if (action.getAttacker() == pokemon) {
                return true;
            }
        }

        return false;
    }

}
