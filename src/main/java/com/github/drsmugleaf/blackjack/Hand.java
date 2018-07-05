package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Hand implements Comparable<Hand> {

    @Nonnull
    private final List<Card> CARDS = new ArrayList<>();

    @Nonnull
    private Actions action = Actions.NONE;

    @Nonnull
    private Status status = Status.WAITING;

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

    int add(@Nonnull Card... cards) {
        Collections.addAll(CARDS, cards);
        return getScore();
    }

    int add(@Nonnull Collection<Card> cards) {
        CARDS.addAll(cards);
        return getScore();
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
    public Actions getAction() {
        return action;
    }

    @Nonnull
    public Set<Actions> getAvailableActions() {
        EnumSet<Actions> actions = EnumSet.noneOf(Actions.class);
        for (Actions action : Actions.values()) {
            if (action.isValidFor(this)) {
                actions.add(action);
            }
        }

        return actions;
    }

    @Nonnull
    public List<Card> getCards() {
        return new ArrayList<>(CARDS);
    }

    @Nonnull
    public Status getStatus() {
        return status;
    }

    @Nonnull
    Card remove(int i) {
        return CARDS.remove(i);
    }

    void reset() {
        CARDS.clear();
    }

    boolean setAction(@Nonnull String actionName) {
        Actions action = Actions.getAction(actionName);
        return setAction(action);
    }

    public int size() {
        return CARDS.size();
    }

    boolean setAction(@Nullable Actions action) {
        if (action != null && status == Status.PLAYING && action.isValidFor(this)) {
            this.action = action;
            return true;
        } else {
            return false;
        }
    }

    void setStatus(@Nonnull Status status) {
        this.status = status;
    }

}
