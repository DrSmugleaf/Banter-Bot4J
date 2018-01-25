package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.trainer.Trainer;

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
    private final List<Action> ACTIONS = new ArrayList<>();

    private boolean executed = false;

    protected Turn(@Nonnull Battle battle) {
        ID = battle.getTurn() + 1;
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
        ACTIONS.add(addAt, ACTIONS.remove(removeFrom));
    }

    public List<Action> getTurnOrder() {
        return new ArrayList<>(ACTIONS);
    }

    protected void execute(@Nonnull Collection<Trainer> trainers) {
        if (executed) {
            throw new IllegalStateException("Turn " + ID + " was already executed");
        }

        executed = true;

        for (Trainer trainer : trainers) {
            ACTIONS.addAll(trainer.getActions());
            trainer.resetChosenMove();
            trainer.resetActions();
        }

        Collections.shuffle(ACTIONS);
        ACTIONS.sort((action1, action2) -> action2.getPriority().compareTo(action1.getPriority()));

        for (Action action : ACTIONS) {
            action.getAttacker().executeTurn();
            action.getAttacker().finishTurn();
        }

        finish();
    }

    protected void finish() {
        ACTIONS.clear();
        POKEMONS_SENT_OUT.clear();
    }

    @Nonnull
    protected List<Action> getHitBy(@Nonnull Pokemon pokemon) {
        List<Action> hitBy = new ArrayList<>();

        for (int i = ACTIONS.size() - 1; i >= 0; i--) {
            Action action = ACTIONS.get(i);
            if (!action.hit().containsKey(pokemon) || !action.hit().get(pokemon)) {
                continue;
            }

            hitBy.add(action);
        }

        return hitBy;
    }

}
