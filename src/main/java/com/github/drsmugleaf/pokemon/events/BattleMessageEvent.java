package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 23/07/2017.
 */
public class BattleMessageEvent extends Event {

    @NotNull
    public final String MESSAGE;

    public BattleMessageEvent(@NotNull Battle battle, @NotNull String message) {
        super(battle);
        MESSAGE = message;
    }

    @NotNull
    public String getMessage() {
        return MESSAGE;
    }

}
