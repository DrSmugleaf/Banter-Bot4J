package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.battle.IModifier;

/**
 * Created by DrSmugleaf on 01/01/2018.
 */
public class Tag implements IModifier {

    private final Tags TAG;

    private int duration;

    @Nullable
    private final Action ACTION;

    protected Tag(Tags tag, @Nullable Action action) {
        TAG = tag;
        duration = TAG.getDuration();
        ACTION = action;
    }

    public Tags getBaseTag() {
        return TAG;
    }

    @Nullable
    public Action getAction() {
        return ACTION;
    }

    @Override
    public void onTurnEnd(Battle battle, Pokemon pokemon) {
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
