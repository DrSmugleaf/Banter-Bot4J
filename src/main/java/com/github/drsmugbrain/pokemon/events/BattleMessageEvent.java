package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Battle;

/**
 * Created by DrSmugleaf on 23/07/2017.
 */
public class BattleMessageEvent extends Event {

    private final String MESSAGE;

    public BattleMessageEvent(Battle battle, String message) {
        super(battle);
        this.MESSAGE = message;
    }

    public String getMessage() {
        return this.MESSAGE;
    }

}
