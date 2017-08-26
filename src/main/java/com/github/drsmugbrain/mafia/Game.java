package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.events.EventDispatcher;
import com.github.drsmugbrain.mafia.events.GameStartEvent;
import com.github.drsmugbrain.mafia.roles.Role;
import com.github.drsmugbrain.mafia.roles.Roles;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public class Game {

    private final Setup SETUP;
    private final Map<Long, Player> PLAYERS;
    private Cycle CYCLE = new Cycle(this, Phase.DAY);

    public Game(@Nonnull Setup setup, @Nonnull Map<Long, Player> players) {
        this.SETUP = setup;
        this.PLAYERS = players;
    }

    public Setup getSetup() {
        return this.SETUP;
    }

    public Map<Long, Player> getPlayers() {
        return this.PLAYERS;
    }

    public void start() {
        List<Player> playersWithoutRoles = new ArrayList<>(this.PLAYERS.values());

        for (Roles role : this.SETUP.getRoles()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(playersWithoutRoles.size());
            Player randomPlayer = playersWithoutRoles.remove(randomIndex);

            randomPlayer.setRole(new Role(role));
        }

        EventDispatcher.dispatch(new GameStartEvent(this));

        this.CYCLE.resume();
    }

    public Cycle getCycle() {
        return this.CYCLE;
    }

}
