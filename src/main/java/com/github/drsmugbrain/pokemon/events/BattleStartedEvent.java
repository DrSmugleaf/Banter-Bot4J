package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.battle.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 18/07/2017.
 */
public class BattleStartedEvent extends Event {

    public BattleStartedEvent(@Nonnull Battle battle) {
        super(battle);
    }

}
