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
    private final List<Player> PLAYERS = new ArrayList<>();

    @Nonnull
    private final Deck DECK;

    Game(@Nonnull List<Long> players) {
        for (Long id : players) {
            PLAYERS.add(new Player(id));
        }

        DECK = new Deck();
    }

}
