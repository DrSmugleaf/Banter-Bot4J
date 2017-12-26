package com.github.drsmugbrain.pokemon.battle;

import com.github.drsmugbrain.pokemon.events.*;
import com.github.drsmugbrain.pokemon.moves.Move;
import com.github.drsmugbrain.pokemon.pokemon.Pokemon;
import com.github.drsmugbrain.pokemon.trainer.Trainer;
import com.github.drsmugbrain.pokemon.trainer.TrainerBuilder;
import com.github.drsmugbrain.pokemon.trainer.TrainerStatus;
import com.github.drsmugbrain.pokemon.trainer.UserException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Battle extends Setup {

    private final Map<Long, Trainer> TRAINERS = new LinkedHashMap<>();
    private final List<Action> TURN_ORDER = new ArrayList<>();
    private Weather weather;
    private final Map<Trainer, List<Pokemon>> POKEMONS_SENT_OUT_THIS_TURN = new HashMap<>();
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
    public Action getAction(Pokemon attacker) {
        for (Action action : getCurrentTurn().getActions()) {
            if (action.getAttacker() == attacker) {
                return action;
            }
        }

        return null;
    }

    public Map<Long, Trainer> getTrainers() {
        return new HashMap<>(TRAINERS);
    }

    public Trainer getTrainer(Long id) {
        return TRAINERS.get(id);
    }

    public List<Action> getTurnOrder() {
        return TURN_ORDER;
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
        POKEMONS_SENT_OUT_THIS_TURN.putIfAbsent(trainer, new ArrayList<>());
        POKEMONS_SENT_OUT_THIS_TURN.get(trainer).add(pokemon);
        trainer.sendOut(pokemon);

        for (Trainer trainer1 : TRAINERS.values()) {
            if (trainer1.getStatus() == TrainerStatus.CHOOSING_POKEMON) {
                return;
            }
        }

        for (Map.Entry<Trainer, List<Pokemon>> trainerListEntry : POKEMONS_SENT_OUT_THIS_TURN.entrySet()) {
            for (Pokemon pokemon1 : trainerListEntry.getValue()) {
                Event event = new TrainerSendOutPokemonEvent(pokemon1);
                EventDispatcher.dispatch(event);
            }
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

    public void executeTurn() { // Move to Turn
        TURNS.add(new Turn(this));

        for (Trainer trainer : TRAINERS.values()) {
            TURN_ORDER.addAll(trainer.getActions());
            trainer.resetChosenMove();
            trainer.resetActions();
        }

        List<Action> ACTIONS = getCurrentTurn().getActions();

        Collections.shuffle(ACTIONS);
        ACTIONS.sort((action1, action2) -> action2.getPriority().compareTo(action1.getPriority()));

        for (Action action : ACTIONS) {
            action.getAttacker().executeTurn();
            action.getAttacker().finishTurn();
        }

        finishTurn();
    }

    private void finishTurn() { // Move to Turn
        List<Action> ACTIONS = getCurrentTurn().getActions();
        ACTIONS.clear();
        POKEMONS_SENT_OUT_THIS_TURN.clear();
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
        return getCurrentTurn().getID();
    }

    @Nonnull
    public Turn getCurrentTurn() {
        return TURNS.get(TURNS.size() - 1);
    }

    @Nonnull
    public Action createAction(@Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon target) {
        Action action = new Action(move, attacker, target, getCurrentTurn().getID());
        getCurrentTurn().addAction(action);
        return action;
    }

    public Action replaceAction(@Nonnull Action oldAction, @Nonnull Move move, @Nonnull Pokemon attacker, @Nonnull Pokemon target) {
        Action action = new Action(move, attacker, target, getCurrentTurn().getID());
        getCurrentTurn().replaceAction(oldAction, action);
        return action;
    }

    @Nullable
    public Action getLastAction() {
        List<Action> ACTIONS = getCurrentTurn().getActions();
        if (ACTIONS.size() == 0) {
            return null;
        }

        return ACTIONS.get(ACTIONS.size() - 1);
    }

    @Nullable
    public Action getLastAction(@Nonnull Pokemon pokemon) {
        List<Action> ACTIONS = getCurrentTurn().getActions();
        for (int i = ACTIONS.size() - 1; i >= 0; i--) {
            Action currentAction = ACTIONS.get(i);
            if (currentAction.getAttacker() == pokemon) {
                return currentAction;
            }
        }

        return null;
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
        List<Action> ACTIONS = getCurrentTurn().getActions();

        for (Action action : ACTIONS) {
            if (action.getTarget() == pokemon) {
                hitBy.add(action);
            }
        }

        return hitBy;
    }

    @Nullable
    public Action getLastHitBy(@Nonnull Pokemon pokemon) {
        List<Action> ACTIONS = getCurrentTurn().getActions();
        for (Action action : ACTIONS) {
            if (action.getTarget() == pokemon) {
                return action;
            }
        }

        return null;
    }

    public boolean movedThisTurn(@Nonnull Pokemon pokemon) {
        List<Action> ACTIONS = getCurrentTurn().getActions();
        for (int i = ACTIONS.size() - 1; i >= 0; i--) {
            Action currentAction = ACTIONS.get(i);

            if (currentAction.getTurn() != getTurn()) {
                break;
            }

            if (currentAction.getAttacker() == pokemon) {
                return true;
            }
        }

        return false;
    }

}
