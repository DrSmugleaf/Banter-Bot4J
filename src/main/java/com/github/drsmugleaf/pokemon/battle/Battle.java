package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.events.*;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.trainer.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 28/09/2017.
 */
public class Battle extends Setup {

    @NotNull
    private final Map<Long, Trainer> TRAINERS = new LinkedHashMap<>();

    @NotNull
    private Weather weather = Weather.NONE;

    @NotNull
    private final List<Turn> TURNS = new ArrayList<>();

    protected Battle(@NotNull Setup setup, @NotNull List<TrainerBuilder> trainers) throws UserException {
        super(setup);

        TURNS.add(new Turn(this));

        for (TrainerBuilder trainerBuilder : trainers) {
            trainerBuilder.setBattle(this);
            Trainer trainer = trainerBuilder.build();
            TRAINERS.put(trainer.ID, trainer);
            trainer.setStatus(TrainerStatus.CHOOSING_POKEMON);
        }

        Event battleEvent = new BattleStartedEvent(this);
        EventDispatcher.dispatch(battleEvent);

        Event trainersEvent = new TrainerChoosingPokemonEvent(TRAINERS.values());
        EventDispatcher.dispatch(trainersEvent);
    }

    @Nullable
    public Action getAction(@NotNull Pokemon attacker) {
        return attacker.getAction();
    }

    @NotNull
    public Map<Long, Trainer> getTrainers() {
        return new HashMap<>(TRAINERS);
    }

    @Nullable
    public Trainer getTrainer(Long id) {
        return TRAINERS.get(id);
    }

    @NotNull
    public Weather getWeather() {
        return weather;
    }

    public void setWeather(@NotNull Weather weather) {
        this.weather = weather;
    }

    protected void removeWeather(@NotNull Weather weather) {
        if (this.weather == weather) {
            this.weather = Weather.NONE;
        }
    }

    public void sendOut(@NotNull Trainer trainer, @NotNull Pokemon pokemon) {
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

    public void addAction(@NotNull Trainer trainer, @NotNull Pokemon pokemon, @NotNull Move move, @NotNull Pokemon target) {
        trainer.addAction(this, move, target, pokemon);

        if (TRAINERS.values().stream().allMatch(t -> t.getStatus() == TrainerStatus.WAITING)) {
            executeTurn();
        }
    }

    private void executeTurn() {
        getCurrentTurn().execute(TRAINERS.values());
        nextTurn();
    }

    private void nextTurn() {
        TURNS.add(new Turn(this));

        for (Trainer trainer : TRAINERS.values()) {
            trainer.finishTurn();
        }

        if (TRAINERS.values().stream().allMatch(trainer -> trainer.getStatus() != TrainerStatus.CHOOSING_POKEMON)) {
            for (Trainer trainer : TRAINERS.values()) {
                for (Pokemon pokemon : trainer.getActivePokemons()) {
                    pokemon.MOVES.resetValid();
                }
            }

            Event event = new BattleTurnStartEvent(this);
            EventDispatcher.dispatch(event);
        }

    }

    @NotNull
    public List<Pokemon> getTargetList(@NotNull Pokemon pokemon) {
        // TODO: 05/09/2018 Proper targeting
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

        return getCurrentTurn().ID;
    }

    @NotNull
    public Turn getCurrentTurn() {
        return TURNS.get(TURNS.size() - 1);
    }

    @NotNull
    public Action createAction(@NotNull Move move, @NotNull Pokemon attacker, @NotNull Pokemon target) {
        return new Action(move, attacker, target, getCurrentTurn().ID);
    }

    @NotNull
    public List<Action> getAllActions() {
        List<Action> actions = new ArrayList<>();

        for (Turn turn : TURNS) {
            actions.addAll(turn.getTurnOrder());
        }

        return actions;
    }

    @NotNull
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
    public Action getLastAction(@NotNull Pokemon pokemon) {
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

    @NotNull
    public Action replaceAction(@NotNull Action oldAction, @NotNull Move move, @NotNull Pokemon attacker, @NotNull Pokemon target) {
        return new Action(move, attacker, target, getCurrentTurn().ID);
    }


    @NotNull
    public Trainer getOppositeTrainer(@NotNull Trainer trainer) {
        List<Trainer> trainers = new ArrayList<>(TRAINERS.values());
        trainers.remove(trainer);
        return trainers.get(0);
    }

    @NotNull
    public List<Action> getHitBy(@NotNull Pokemon pokemon) {
        List<Action> hitBy = new ArrayList<>();

        for (Turn turn : TURNS) {
            hitBy.addAll(turn.getHitsToward(pokemon));
        }

        return hitBy;
    }

    @Nullable
    public Action getLastHitBy(@NotNull Pokemon pokemon) {
        List<Action> hitBy = getHitBy(pokemon);

        if (hitBy.isEmpty()) {
            return null;
        }

        return hitBy.get(hitBy.size() - 1);
    }

    public boolean wasHitByThisTurn(@NotNull Pokemon pokemon, @NotNull BaseMove move) {
        List<Action> hitBy = getCurrentTurn().getHitsToward(pokemon);

        for (Action action : hitBy) {
            if (action.BASE_MOVE == move) {
                return true;
            }
        }

        return false;
    }

    public boolean movedThisTurn(@NotNull Pokemon pokemon) {
        for (Action action : getCurrentTurn().getTurnOrder()) {
            if (action.getAttacker() == pokemon) {
                return true;
            }
        }

        return false;
    }

}
