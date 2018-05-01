package com.github.drsmugleaf.blackjack.decks.french;

import com.github.drsmugleaf.blackjack.decks.Cards;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public enum FrenchCards implements Cards {

    ACE("Ace", 11),
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 10),
    QUEEN("Queen", 10),
    KING("King", 10);

    final String NAME;
    final int VALUE;

    FrenchCards(@Nonnull String name, int value) {
        NAME = name;
        VALUE = value;
    }

    @Nonnull
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getValue() {
        return VALUE;
    }

}
