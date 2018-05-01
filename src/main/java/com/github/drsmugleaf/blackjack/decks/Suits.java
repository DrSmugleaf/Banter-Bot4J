package com.github.drsmugleaf.blackjack.decks;

import com.github.drsmugleaf.blackjack.decks.french.FrenchSuits;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public interface Suits {

    @Nonnull
    Suits[] DEFAULT = FrenchSuits.values();

    @Nonnull
    String getName();

    char getSymbol();

}
