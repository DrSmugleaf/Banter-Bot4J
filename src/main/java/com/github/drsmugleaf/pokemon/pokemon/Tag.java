package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.battle.IModifier;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Battle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DrSmugleaf on 01/01/2018.
 */
public class Tag implements IModifier {

    @NotNull
    private final Tags TAG;

    private int duration;

    @Nullable
    private final Action ACTION;

    protected Tag(@NotNull Tags tag, @Nullable Action action) {
        TAG = tag;
        duration = TAG.getDuration();
        ACTION = action;
    }

    @NotNull
    public Tags getBaseTag() {
        return TAG;
    }

    @Nullable
    public Action getAction() {
        return ACTION;
    }

    @Override
    public void onTurnEnd(@NotNull Battle battle, @NotNull Pokemon pokemon) {
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
