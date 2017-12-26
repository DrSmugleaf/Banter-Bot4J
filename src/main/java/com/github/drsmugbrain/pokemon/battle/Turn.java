package com.github.drsmugbrain.pokemon.battle;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 26/12/2017.
 */
public class Turn {

    private final int ID;

    @Nonnull
    private final List<Action> ACTIONS = new ArrayList<>();

    protected Turn(@Nonnull Battle battle) {
        ID = battle.getTurn();
    }

    public int getID() {
        return ID;
    }

    protected void addAction(@Nonnull Action action) {
        ACTIONS.add(action);
    }

    @Nonnull
    protected List<Action> getActions() {
        return new ArrayList<>(ACTIONS);
    }

    protected void replaceAction(@Nonnull Action oldAction, @Nonnull Action newAction) {
        int index = ACTIONS.indexOf(oldAction);
        ACTIONS.set(index, newAction);
    }

}
