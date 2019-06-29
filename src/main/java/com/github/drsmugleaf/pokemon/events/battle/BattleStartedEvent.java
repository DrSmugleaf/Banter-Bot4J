package com.github.drsmugleaf.pokemon.events.battle;

import com.github.drsmugleaf.pokemon.battle.Battle;
import com.github.drsmugleaf.pokemon.events.Event;

/**
 * Created by DrSmugleaf on 18/07/2017.
 */
public class BattleStartedEvent extends Event {

    public BattleStartedEvent(Battle battle) {
        super(battle);
    }

}
