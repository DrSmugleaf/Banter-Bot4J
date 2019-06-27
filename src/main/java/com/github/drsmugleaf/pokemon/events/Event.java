package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class Event {

    public final Battle BATTLE;

    public Event(Battle battle) {
        BATTLE = battle;
    }

    @Contract(pure = true)
    public Battle getBattle() {
        return BATTLE;
    }

}
