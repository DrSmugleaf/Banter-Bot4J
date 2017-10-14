package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.battle.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/07/2017.
 */
public class BattleMessageEvent extends Event {

    private final String MESSAGE;

    public BattleMessageEvent(@Nonnull Battle battle, @Nonnull String message) {
        super(battle);
        this.MESSAGE = message;
    }

    @Nonnull
    public String getMessage() {
        return this.MESSAGE;
    }

}
