package com.github.drsmugbrain.pokemon.battle;

import com.github.drsmugbrain.pokemon.pokemon.Pokemon;
import com.github.drsmugbrain.pokemon.trainer.Trainer;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 26/12/2017.
 */
public class Turn {

    private final int ID;

    @Nonnull
    private final Map<Trainer, Pokemon> POKEMONS_SENT_OUT = new HashMap<>();

    @Nonnull
    private final List<Action> TURN_ORDER = new ArrayList<>();

    private boolean executed = false;

    protected Turn(@Nonnull Battle battle) {
        ID = battle.getTurn();
    }

    public int getID() {
        return ID;
    }

    @Nonnull
    public Map<Trainer, Pokemon> getPokemonsSentOut() {
        return new HashMap<>(POKEMONS_SENT_OUT);
    }

    protected void sendOut(@Nonnull Trainer trainer, @Nonnull Pokemon pokemon) {
        POKEMONS_SENT_OUT.put(trainer, pokemon);
    }

    public void changeTurnOrder(int addAt, int removeFrom) {
        TURN_ORDER.add(addAt, TURN_ORDER.remove(removeFrom));
    }

    public List<Action> getTurnOrder() {
        return new ArrayList<>(TURN_ORDER);
    }

    protected void execute(@Nonnull Collection<Trainer> trainers) {
        if (executed) {
            throw new IllegalStateException("Turn " + ID + " was already executed");
        }

        executed = true;

        for (Trainer trainer : trainers) {
            TURN_ORDER.addAll(trainer.getActions());
            trainer.resetChosenMove();
            trainer.resetActions();
        }

        Collections.shuffle(TURN_ORDER);
        TURN_ORDER.sort((action1, action2) -> action2.getPriority().compareTo(action1.getPriority()));

        for (Action action : TURN_ORDER) {
            action.getAttacker().executeTurn();
            action.getAttacker().finishTurn();
        }

        finish();
    }

    protected void finish() {
        TURN_ORDER.clear();
        POKEMONS_SENT_OUT.clear();
    }

}
