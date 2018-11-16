package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class Event {

    @NotNull
    public final Battle BATTLE;

    public Event(@NotNull Battle battle) {
        BATTLE = battle;
    }

    @NotNull
    public Battle getBattle() {
        return BATTLE;
    }

}
