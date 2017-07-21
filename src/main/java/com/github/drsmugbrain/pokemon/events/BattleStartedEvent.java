package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Battle;

/**
 * Created by DrSmugleaf on 18/07/2017.
 */
public class BattleStartedEvent extends Event {

    public BattleStartedEvent(Battle battle) {
        super(battle);
    }

}
