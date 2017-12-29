package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class Event {

    private final Battle BATTLE;

    public Event(@Nonnull Battle battle) {
        this.BATTLE = battle;
    }

    @Nonnull
    public Battle getBattle() {
        return this.BATTLE;
    }

}
