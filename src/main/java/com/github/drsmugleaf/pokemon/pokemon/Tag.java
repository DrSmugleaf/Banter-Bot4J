package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.IModifier;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/01/2018.
 */
public class Tag implements IModifier {

    private final Tags TAG;
    private int duration;
    private final Action ACTION;

    public Tag(@Nonnull Tags tag, @Nonnull Action action) {
        TAG = tag;
        duration = TAG.getDuration();
        ACTION = action;
    }

    @Override
    public void onTurnEnd(@Nonnull Battle battle, @Nonnull Pokemon pokemon) {
        duration--;
        if (duration < 0) {
            TAG.remove(pokemon, ACTION);
        }
    }

}
