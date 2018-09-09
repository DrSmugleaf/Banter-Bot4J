package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.battle.IModifier;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 01/01/2018.
 */
public class Tag implements IModifier {

    @Nonnull
    private final Tags TAG;

    private int duration;

    @Nullable
    private final Action ACTION;

    protected Tag(@Nonnull Tags tag, @Nullable Action action) {
        TAG = tag;
        duration = TAG.getDuration();
        ACTION = action;
    }

    @Nonnull
    public Tags getBaseTag() {
        return TAG;
    }

    @Nullable
    public Action getAction() {
        return ACTION;
    }

    @Override
    public void onTurnEnd(@Nonnull Battle battle, @Nonnull Pokemon pokemon) {
        duration--;

        if (duration < 0) {
            if (ACTION == null) {
                TAG.remove(pokemon);
            } else {
                TAG.remove(pokemon, ACTION);
            }
        }
    }

}
