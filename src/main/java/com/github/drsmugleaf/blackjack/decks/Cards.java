package com.github.drsmugleaf.blackjack.decks;

import com.github.drsmugleaf.blackjack.decks.french.FrenchCards;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public interface Cards {

    @Nonnull
    Cards[] DEFAULT = FrenchCards.values();

    @Nonnull
    String getName();

    int getValue();

}
