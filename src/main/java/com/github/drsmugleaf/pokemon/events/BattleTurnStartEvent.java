package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class BattleTurnStartEvent extends Event {

    public BattleTurnStartEvent(@Nonnull Battle battle) {
        super(battle);
    }

}
