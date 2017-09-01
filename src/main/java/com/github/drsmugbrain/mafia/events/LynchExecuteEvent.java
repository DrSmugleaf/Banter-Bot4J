package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public class LynchExecuteEvent extends TrialEvent {

    public LynchExecuteEvent(@Nonnull Game game, @Nonnull Player player) {
        super(game, player);
    }

}
