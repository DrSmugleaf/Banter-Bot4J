package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Battle;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class Event {

    private final Battle BATTLE;

    public Event(Battle battle) {
        this.BATTLE = battle;
    }

    public Battle getBattle() {
        return this.BATTLE;
    }

}
