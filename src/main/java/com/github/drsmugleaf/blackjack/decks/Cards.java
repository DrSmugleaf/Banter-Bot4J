package com.github.drsmugleaf.blackjack.decks;

import com.github.drsmugleaf.blackjack.Hand;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public interface Cards {

    @Nonnull
    String getName();

    int getValue(@Nonnull Hand hand);

}
