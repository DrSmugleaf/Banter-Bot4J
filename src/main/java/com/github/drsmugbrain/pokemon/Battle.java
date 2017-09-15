package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Battle {

    protected final List<Pokemon> TURN_ORDER = new ArrayList<>();
    private final Generation GENERATION;
    private final Map<Long, Trainer> TRAINERS = new LinkedHashMap<>();
    private Weather weather;
    private boolean ready = false;
    private int TURN_NUMBER = 1;
    private final Map<Trainer, List<Pokemon>> POKEMONS_SENT_OUT_THIS_TURN = new HashMap<>();
    private final TreeMap<Pokemon, Move> ACTIONS = new TreeMap<>();

    public Battle(Generation generation, @Nonnull Long id1, @Nonnull Trainer trainer1, @Nonnull Long id2, @Nonnull Trainer trainer2) {
        this.GENERATION = generation;
        trainer1.setBattle(this);
        trainer2.setBattle(this);
        for (Pokemon pokemon : trainer1.getPokemons()) {
            pokemon.setBattle(this);
        }
        for (Pokemon pokemon : trainer2.getPokemons()) {
            pokemon.setBattle(this);
        }
        BattleStartedEvent event = new BattleStartedEvent(this);
        EventDispatcher.dispatch(event);
        trainer1.setStatus(TrainerStatus.CHOOSING_POKEMON);
        trainer2.setStatus(TrainerStatus.CHOOSING_POKEMON);
        TRAINERS.put(id1, trainer1);
        TRAINERS.put(id2, trainer2);
        TrainerChoosingPokemonEvent trainerEvent = new TrainerChoosingPokemonEvent(trainer1, trainer2);
        EventDispatcher.dispatch(trainerEvent);
    }

    @Nonnull
    public Generation getGeneration() {
        return this.GENERATION;
    }

    public void executeTurn() {
        for (Trainer trainer : this.TRAINERS.values()) {
            this.TURN_ORDER.addAll(trainer.getActivePokemons());
            trainer.resetChosenMove();
            trainer.resetActions();
        }

        this.TURN_ORDER.sort((pokemon1, pokemon2) -> {
            Move action1 = pokemon1.getAction();
            Move action2 = pokemon2.getAction();

            if (Objects.equals(action1.getBaseMove().getName(), "Pursuit") && action2.getBaseMove() == BaseMove.SWITCH) {
                return 1;
            }
            if (Objects.equals(action2.getBaseMove().getName(), "Pursuit") && action1.getBaseMove() == BaseMove.SWITCH) {
                return -1;
            }

            if (action1.getBaseMove() == BaseMove.SWITCH) {
                return 1;
            }
            if (action2.getBaseMove() == BaseMove.SWITCH) {
                return -1;
            }

            if (action1.getPriority() > action2.getPriority()) {
                return 1;
            }
            if (action2.getPriority() < action1.getPriority()) {
                return -1;
            }

            if (pokemon1.getCurrentStat(Stat.SPEED) > pokemon2.getCurrentStat(Stat.SPEED)) {
                return 1;
            }
            if (pokemon2.getCurrentStat(Stat.SPEED) < pokemon1.getCurrentStat(Stat.SPEED)) {
                return -1;
            }

            if (Math.random() < 0.5) {
                return 1;
            } else {
                return -1;
            }
        });

        for (Pokemon pokemon : this.TURN_ORDER) {
            pokemon.executeTurn(this);
            pokemon.finishTurn();
        }

        this.finishTurn();
    }

    public Map<Long, Trainer> getTrainers() {
        return this.TRAINERS;
    }

    public Trainer getTrainer(Long id) {
        return this.TRAINERS.get(id);
    }

    @Nonnull
    protected Trainer getTrainer(Pokemon pokemon) {
        for (Trainer trainer : this.getTrainers().values()) {
            if (trainer.hasPokemon(pokemon)) {
                return trainer;
            }
        }

        throw new IllegalArgumentException("Pokemon " + pokemon.getName() + " isn't owned by any trainer in this battle");
    }

    public boolean executeTurnReady() {
        for (Trainer trainer : this.TRAINERS.values()) {
            for (Pokemon pokemon : trainer.getActivePokemons()) {
                if (!trainer.hasAction(pokemon)) {
                    return false;
                }
            }
        }

        return true;
    }

    protected Weather getWeather() {
        return this.weather;
    }

    protected void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Nonnull
    public List<Pokemon> getEnemies(Trainer trainer, Pokemon pokemon) {
        List<Pokemon> enemyActivePokemons = new ArrayList<>();

        for (Trainer trainer1 : this.TRAINERS.values()) {
            if (trainer1 == trainer) continue;
            enemyActivePokemons.addAll(trainer1.getActivePokemons());
        }

        return enemyActivePokemons;
    }

    public void sendOut(Trainer trainer, Pokemon pokemon) {
        this.POKEMONS_SENT_OUT_THIS_TURN.putIfAbsent(trainer, new ArrayList<>());
        this.POKEMONS_SENT_OUT_THIS_TURN.get(trainer).add(pokemon);
        trainer.sendOut(pokemon);

        for (Trainer trainer1 : this.getTrainers().values()) {
            if (trainer1.getStatus() == TrainerStatus.CHOOSING_POKEMON) {
                return;
            }
        }

        for (Map.Entry<Trainer, List<Pokemon>> trainerListEntry : this.POKEMONS_SENT_OUT_THIS_TURN.entrySet()) {
            for (Pokemon pokemon1 : trainerListEntry.getValue()) {
                Event event = new TrainerSendOutPokemonEvent(pokemon1);
                EventDispatcher.dispatch(event);
            }
        }

        for (Trainer trainer1 : this.getTrainers().values()) {
            trainer1.setPokemonInFocus(trainer1.getActivePokemons().get(0));
            trainer1.setStatus(TrainerStatus.CHOOSING_MOVE);
        }

        Event event = new BattleTurnStartEvent(this);
        EventDispatcher.dispatch(event);
    }

    public boolean isReady() {
        return this.ready;
    }

    protected void setReady(boolean bool) {
        this.ready = bool;
    }

    public void addAction(Trainer trainer, Pokemon pokemon, Move move, Pokemon target) {
        if (move.getBaseMove() != BaseMove.SWITCH) {
            this.ACTIONS.put(pokemon, move);
        }
        trainer.addAction(pokemon, move, target);
        for (Trainer trainer1 : this.TRAINERS.values()) {
            if (trainer1.getStatus() != TrainerStatus.WAITING) {
                return;
            }
        }

        this.executeTurn();
    }

    private void finishTurn() {
        this.TURN_ORDER.clear();
        this.POKEMONS_SENT_OUT_THIS_TURN.clear();
        this.TURN_NUMBER++;

        for (Trainer trainer : this.getTrainers().values()) {
            trainer.finishTurn();
        }

        if (this.getTrainers().values().stream().allMatch(trainer -> trainer.getStatus() != TrainerStatus.CHOOSING_POKEMON)) {
            BattleTurnStartEvent event = new BattleTurnStartEvent(this);
            EventDispatcher.dispatch(event);
        }
    }

    public List<Pokemon> getTargetList() {
        List<Pokemon> targets = new ArrayList<>();

        for (Trainer trainer : this.getTrainers().values()) {
            targets.addAll(trainer.getActivePokemons());
        }

        return targets;
    }

    public int getTurnNumber() {
        return this.TURN_NUMBER;
    }

    protected TreeMap<Pokemon, Move> getActions() {
        return this.ACTIONS;
    }

}
