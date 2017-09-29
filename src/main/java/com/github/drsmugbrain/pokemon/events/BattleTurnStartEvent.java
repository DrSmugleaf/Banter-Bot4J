package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class BattleTurnStartEvent extends Event {

    public BattleTurnStartEvent(@Nonnull Battle battle) {
        super(battle);
    }

}
