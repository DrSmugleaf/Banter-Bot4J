package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.blackjack.decks.Deck;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 28/06/2018
 */
public class GameBuilder {

    @Nonnull
    public Deck deck = new Deck();

    @Nonnull
    public final List<Long> PLAYERS = new ArrayList<>();

    public GameBuilder(@Nonnull Deck deck, @Nonnull List<Long> players) {
        this.deck = deck;
        PLAYERS.addAll(players);
    }

    public GameBuilder(@Nonnull Deck deck) {
        this.deck = deck;
    }

    public GameBuilder(@Nonnull List<Long> players) {
        PLAYERS.addAll(players);
    }

    public GameBuilder() {}

    public Game build() {
        return new Game(deck, PLAYERS);
    }

}
