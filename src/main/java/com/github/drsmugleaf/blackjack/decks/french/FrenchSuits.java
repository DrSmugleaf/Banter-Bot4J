package com.github.drsmugleaf.blackjack.decks.french;

import com.github.drsmugleaf.blackjack.decks.Suits;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public enum FrenchSuits implements Suits {

    CLUBS("Clubs", '♣'),
    DIAMONDS("Diamonds", '♦'),
    HEARTS("Hearts", '♥'),
    SPADES("Spades", '♠');

    @Nonnull
    final String NAME;

    final char SYMBOL;

    FrenchSuits(@Nonnull String name, char symbol) {
        NAME = name;
        SYMBOL = symbol;
    }

    @Nonnull
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

}
