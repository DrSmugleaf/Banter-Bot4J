package com.github.drsmugleaf.blackjack.decks;

import com.github.drsmugleaf.blackjack.decks.french.FrenchCards;
import com.github.drsmugleaf.blackjack.decks.french.FrenchSuits;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Deck<T extends Enum<T> & Cards, E extends Enum & Suits> {

    @Nonnull
    private final Class<T> CARDS;

    @Nonnull
    private final Class<E> SUITS;

    public Deck(@Nonnull Class<T> cards, @Nonnull Class<E> suits) {
        CARDS = cards;
        SUITS = suits;
    }

    @SuppressWarnings("unchecked")
    public Deck() {
        this((Class<T>) FrenchCards.class, (Class<E>) FrenchSuits.class);
    }

}
