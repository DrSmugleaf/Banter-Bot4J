package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 28/06/2018
 */
public class GameBuilder {

    @Nonnull
    private Deck deck = new Deck();

    @Nonnull
    private final Set<Long> PLAYERS = new LinkedHashSet<>();

    public GameBuilder(@Nonnull Deck deck, @Nonnull Long... players) {
        this.deck = deck;
        Collections.addAll(PLAYERS, players);
    }

    public GameBuilder(@Nonnull Deck deck) {
        this.deck = deck;
    }

    public GameBuilder(@Nonnull Long... players) {
        Collections.addAll(PLAYERS, players);
    }

    public GameBuilder() {}

    @Nonnull
    public static Game buildDefault() {
        return new Game(new Deck(), new LinkedHashSet<>());
    }

    public GameBuilder setDeck(@Nonnull Deck deck) {
        this.deck = deck;
        return this;
    }

    public GameBuilder addPlayer(@Nonnull Long... id) {
        Collections.addAll(PLAYERS, id);
        return this;
    }

    public Set<Long> getPlayers() {
        return new HashSet<>(PLAYERS);
    }

    public boolean hasPlayer(@Nonnull Long id) {
        return PLAYERS.contains(id);
    }

    public GameBuilder removePlayer(@Nonnull Long... id) {
        PLAYERS.removeAll(Arrays.asList(id));
        return this;
    }

    @Nonnull
    public Game build() {
        return new Game(deck, PLAYERS);
    }

}
