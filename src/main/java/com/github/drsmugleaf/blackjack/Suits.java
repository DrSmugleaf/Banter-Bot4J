package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public enum Suits {

    CLUBS("Clubs", '♣'),
    DIAMONDS("Diamonds", '♦'),
    HEARTS("Hearts", '♥'),
    SPADES("Spades", '♠');

    final String NAME;
    final char SYMBOL;

    Suits(@Nonnull String name, @Nonnull char symbol) {
        NAME = name;
        SYMBOL = symbol;
    }

}
