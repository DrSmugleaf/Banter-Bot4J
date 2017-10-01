package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.DiscordException;
import com.github.drsmugbrain.pokemon.events.*;

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
    private int turn = 1;
    private final Map<Trainer, List<Pokemon>> POKEMONS_SENT_OUT_THIS_TURN = new HashMap<>();
    private final List<Action> ACTIONS = new ArrayList<>();

    protected Battle(@Nonnull Setup setup, @Nonnull List<TrainerBuilder> trainers) throws DiscordException {
        super(setup);

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
    protected Action getAction(Pokemon attacker) {
        for (Action action : ACTIONS) {
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

    protected List<Action> getTurnOrder() {
        return TURN_ORDER;
    }

    protected Weather getWeather() {
        return weather;
    }

    protected void setWeather(Weather weather) {
        this.weather = weather;
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
        trainer.addAction(pokemon, move, target);
        for (Trainer trainer1 : TRAINERS.values()) {
            if (trainer1.getStatus() != TrainerStatus.WAITING) {
                return;
            }
        }

        executeTurn();
    }

    public void executeTurn() {
        for (Trainer trainer : TRAINERS.values()) {
            TURN_ORDER.addAll(trainer.getActions());
            trainer.resetChosenMove();
            trainer.resetActions();
        }

        Collections.shuffle(ACTIONS);
        ACTIONS.sort((action1, action2) -> action2.getPriority().compareTo(action1.getPriority()));

        for (Action action : ACTIONS) {
            action.getAttacker().executeTurn();
            action.getAttacker().finishTurn();
        }

        finishTurn();
    }

    private void finishTurn() {
        ACTIONS.clear();
        POKEMONS_SENT_OUT_THIS_TURN.clear();
        turn++;

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
        return turn;
    }

    @Nullable
    protected Action getLastAction() {
        if (ACTIONS.size() == 0) {
            return null;
        }

        return ACTIONS.get(ACTIONS.size() - 1);
    }

    @Nullable
    protected Action getLastAction(@Nonnull Pokemon pokemon) {
        for (int i = ACTIONS.size() - 1; i >= 0; i--) {
            Action currentAction = ACTIONS.get(i);
            if (currentAction.getAttacker() == pokemon) {
                return currentAction;
            }
        }

        return null;
    }

    @Nonnull
    protected Trainer getOppositeTrainer(@Nonnull Trainer trainer) {
        List<Trainer> trainers = new ArrayList<>(TRAINERS.values());
        trainers.remove(trainer);
        return trainers.get(0);
    }

    @Nonnull
    protected List<Action> getHitBy(@Nonnull Pokemon pokemon) {
        List<Action> hitBy = new ArrayList<>();

        for (Action action : ACTIONS) {
            if (action.getDefender() == pokemon) {
                hitBy.add(action);
            }
        }

        return hitBy;
    }

    @Nullable
    protected Action getLastHitBy(@Nonnull Pokemon pokemon) {
        for (Action action : ACTIONS) {
            if (action.getDefender() == pokemon) {
                return action;
            }
        }

        return null;
    }

    protected boolean movedThisTurn(@Nonnull Pokemon pokemon) {
        for (int i = ACTIONS.size() - 1; i >= 0; i--) {
            Action currentAction = ACTIONS.get(i);

            if (currentAction.getTurn() != turn) {
                break;
            }

            if (currentAction.getAttacker() == pokemon) {
                return true;
            }
        }

        return false;
    }

}
