package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/06/2018
 */
public class Dealer {

    @Nonnull
    final Hand HAND = new Hand();

    Dealer() {}

    @Override
    public String toString() {
        return "Dealer's hand: " + HAND;
    }

    void reset() {
        HAND.reset();
    }

}
