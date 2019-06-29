package com.github.drsmugleaf.pokemon.events.battle;

import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.events.Event;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class BattleTurnStartEvent extends Event {

    public BattleTurnStartEvent(Battle battle) {
        super(battle);
    }

}
