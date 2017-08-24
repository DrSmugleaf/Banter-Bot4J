package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Game {

    private final Setup SETUP;
    private final Map<Integer, Player> PLAYERS;
    private Cycles CYCLE = Cycles.DAY;

    Game(@Nonnull Setup setup, @Nonnull Map<Integer, Player> players) {
        this.SETUP = setup;
        this.PLAYERS = players;
    }

    public Setup getSetup() {
        return this.SETUP;
    }

    public Map<Integer, Player> getPlayers() {
        return this.PLAYERS;
    }

    public Cycles getCycle() {
        return this.CYCLE;
    }

    protected void setCycle(@Nonnull Cycles cycle) {
        this.CYCLE = cycle;
    }

}
