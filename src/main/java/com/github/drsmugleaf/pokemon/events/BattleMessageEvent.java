package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.battle.Battle;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 23/07/2017.
 */
public class BattleMessageEvent extends Event {

    public final String MESSAGE;

    public BattleMessageEvent(Battle battle, String message) {
        super(battle);
        MESSAGE = message;
    }

    @Contract(pure = true)
    public String getMessage() {
        return MESSAGE;
    }

}
