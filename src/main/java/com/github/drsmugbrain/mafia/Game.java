package com.github.drsmugbrain.mafia;

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
    private final Map<Integer, Player> PLAYERS;
    private Cycle CYCLE = new Cycle(Cycles.DAY);

    public Game(@Nonnull Setup setup, @Nonnull Map<Integer, Player> players) {
        this.SETUP = setup;
        this.PLAYERS = players;
    }

    public Setup getSetup() {
        return this.SETUP;
    }

    public Map<Integer, Player> getPlayers() {
        return this.PLAYERS;
    }

    public void start() {
        List<Player> playersWithoutRoles = new ArrayList<>(this.PLAYERS.values());

        for (Roles role : this.SETUP.getRoles()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(playersWithoutRoles.size());
            Player randomPlayer = playersWithoutRoles.remove(randomIndex);

            randomPlayer.setRole(new Role(role));
        }


    }

    public Cycle getCycle() {
        return this.CYCLE;
    }

    protected void setCycle(@Nonnull Cycle cycle) {
        this.CYCLE = cycle;
    }

}
