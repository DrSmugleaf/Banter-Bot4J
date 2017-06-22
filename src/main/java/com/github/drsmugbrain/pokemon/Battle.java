package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Battle {

    private final Map<Long, Trainer> TRAINERS = new LinkedHashMap<>();
    protected final List<Pokemon> TURN_ORDER = new ArrayList<>();

    public Battle(@Nonnull Long id1, @Nonnull Trainer trainer1, @Nonnull Long id2, @Nonnull Trainer trainer2) {
        TRAINERS.put(id1, trainer1);
        TRAINERS.put(id2, trainer2);
    }

    public void executeTurn() {
        for (Trainer trainer : this.TRAINERS.values()) {
            this.TURN_ORDER.addAll(Arrays.asList(trainer.getActivePokemons()));
        }

        this.TURN_ORDER.sort((pokemon1, pokemon2) -> {
            Move action1 = pokemon1.getAction();
            Move action2 = pokemon2.getAction();

            if (Objects.equals(action1.getName(), "Pursuit") && action2 == BaseMove.SWITCH) {
                return 1;
            }
            if (Objects.equals(action2.getName(), "Pursuit") && action1 == BaseMove.SWITCH) {
                return -1;
            }

            if (action1 == BaseMove.SWITCH) {
                return 1;
            }
            if (action2 == BaseMove.SWITCH) {
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
        }
    }

    public Map<Long, Trainer> getTrainers() {
        return this.TRAINERS;
    }

}
