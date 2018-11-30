package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game {

    @NotNull
    private final Board BOARD;

    @NotNull
    private final List<Player> PLAYERS = new ArrayList<>();

    Game(@NotNull Board board, @NotNull Collection<Player> players) {
        BOARD = board;
        PLAYERS.addAll(players);
    }

    @NotNull
    public List<Player> getPlayers() {
        return new ArrayList<>(PLAYERS);
    }

}
