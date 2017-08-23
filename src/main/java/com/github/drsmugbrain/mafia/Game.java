package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Role;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Game {

    private final Map<Integer, Role> PLAYERS;

    Game(@Nonnull Map<Integer, Role> players) {
        this.PLAYERS = players;
    }

    public Map<Integer, Role> getPlayers() {
        return this.PLAYERS;
    }

}
