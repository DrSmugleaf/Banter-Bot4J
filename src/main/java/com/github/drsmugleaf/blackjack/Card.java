package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.blackjack.decks.Cards;
import com.github.drsmugleaf.blackjack.decks.Suits;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Card {

    @Nonnull
    final Cards CARD;

    @Nonnull
    final Suits SUIT;

    Card(@Nonnull Cards card, @Nonnull Suits suit) {
        CARD = card;
        SUIT = suit;
    }

    @Override
    public String toString() {
        return SUIT.getSymbol() + CARD.getName();
    }

    public int getValue(@Nonnull Hand hand) {
        return CARD.getValue(hand);
    }

}
