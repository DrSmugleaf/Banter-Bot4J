package com.github.drsmugleaf.charactersheets.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class Turn {

    private final long NUMBER = 1;
    private final List<Runnable> RECURRING_EFFECTS = new ArrayList<>();

    public Turn() {}

    public void addEffect(Runnable effect) {
        RECURRING_EFFECTS.add(effect);
    }

    public void execute() {
        for (Runnable effect : RECURRING_EFFECTS) {
            effect.run();
        }
    }

}
