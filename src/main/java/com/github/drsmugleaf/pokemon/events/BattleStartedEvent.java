package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 18/07/2017.
 */
public class BattleStartedEvent extends Event {

    public BattleStartedEvent(@NotNull Battle battle) {
        super(battle);
    }

}
