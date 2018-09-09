package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/07/2017.
 */
public class BattleMessageEvent extends Event {

    @Nonnull
    public final String MESSAGE;

    public BattleMessageEvent(@Nonnull Battle battle, @Nonnull String message) {
        super(battle);
        MESSAGE = message;
    }

    @Nonnull
    public String getMessage() {
        return MESSAGE;
    }

}
