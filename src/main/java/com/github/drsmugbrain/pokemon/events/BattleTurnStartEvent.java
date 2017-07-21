package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Battle;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class BattleTurnStartEvent extends Event {

    public BattleTurnStartEvent(Battle battle) {
        super(battle);
    }

}
