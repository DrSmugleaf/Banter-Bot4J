package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.blackjack.decks.Deck;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Game {

    @Nonnull
    private final Deck DECK;

    @Nonnull
    private final Map<Long, Player> PLAYERS = new LinkedHashMap<>();

    Game(@Nonnull Deck deck, @Nonnull Set<Long> players) {
        DECK = deck;
        for (Long id : players) {
            PLAYERS.put(id, new Player(id));
        }
    }

    public void addPlayer(@Nonnull Long id) {
        PLAYERS.put(id, new Player(id));
    }

    public Player getPlayer(@Nonnull Long id) {
        return PLAYERS.get(id);
    }

    public boolean hasPlayer(@Nonnull Long id) {
        return PLAYERS.containsKey(id);
    }

    public void removePlayer(@Nonnull Long id) {
        PLAYERS.remove(id);
    }

}
