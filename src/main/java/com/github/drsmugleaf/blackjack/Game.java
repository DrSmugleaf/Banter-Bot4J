package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.blackjack.decks.Deck;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Game {

    @Nonnull
    private final Deck DECK;

    @Nonnull
    private final List<Player> PLAYERS = new ArrayList<>();

    Game(@Nonnull Deck deck, @Nonnull List<Long> players) {
        DECK = deck;
        for (Long id : players) {
            PLAYERS.add(new Player(id));
        }
    }

}
