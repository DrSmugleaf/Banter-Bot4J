package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Hand implements Comparable<Hand> {

    @Nonnull
    private final List<Card> CARDS = new ArrayList<>();

    Hand() {}

    @Override
    public int compareTo(@Nonnull Hand o) {
        return getScore().compareTo(o.getScore());
    }

    @Nonnull
    Integer getScore() {
        int score = 0;

        for (Card card : CARDS) {
            score += card.getValue();
        }

        return score;
    }

    @Nonnull
    Card get(int i) {
        return CARDS.get(i);
    }

    int size() {
        return CARDS.size();
    }

    int add(@Nonnull Card... cards) {
        Collections.addAll(CARDS, cards);
        return getScore();
    }

    int add(@Nonnull Collection<Card> cards) {
        CARDS.addAll(cards);
        return getScore();
    }

    void reset() {
        CARDS.clear();
    }

}
