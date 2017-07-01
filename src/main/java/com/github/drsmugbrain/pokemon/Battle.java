package com.github.drsmugbrain.pokemon;

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

    public Battle(Generation generation, @Nonnull Trainer trainer1, @Nonnull Long id2, @Nonnull Trainer trainer2, @Nonnull Long id1) {
        this.GENERATION = generation;
        TRAINERS.put(id1, trainer1);
        TRAINERS.put(id2, trainer2);
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
            pokemon.executeTurn();
            pokemon.finishTurn();
        }
    }

    public Map<Long, Trainer> getTrainers() {
        return this.TRAINERS;
    }

    public Trainer getTrainer(Long id) {
        return this.TRAINERS.get(id);
    }

    public boolean ready() {
        for (Trainer trainer : this.TRAINERS.values()) {
            if (trainer.getActivePokemons().isEmpty()) return false;
        }

        return true;
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

}
