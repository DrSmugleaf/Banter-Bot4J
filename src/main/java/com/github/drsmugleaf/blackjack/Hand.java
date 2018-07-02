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
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Card card : CARDS) {
            builder.append(card);
        }

        return builder.toString();
    }

    @Override
    public int compareTo(@Nonnull Hand o) {
        return getScore().compareTo(o.getScore());
    }

    @Nonnull
    public Integer getScore() {
        Integer score = 0;
        int aces = 0;

        for (Card card : CARDS) {
            int value = card.getValue(this);
            score += value;

            if (value == 11) {
                aces++;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }

    @Nonnull
    Card get(int i) {
        return CARDS.get(i);
    }

    @Nonnull
    public List<Card> getCards() {
        return new ArrayList<>(CARDS);
    }

    public int size() {
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
