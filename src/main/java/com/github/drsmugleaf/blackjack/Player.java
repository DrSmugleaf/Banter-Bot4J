package com.github.drsmugleaf.blackjack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Player {

    public final long ID;

    @Nonnull
    private final List<Hand> HANDS = new ArrayList<>();

    Player(long id) {
        ID = id;
        HANDS.add(new Hand());
    }

    void reset() {
        HANDS.clear();
        HANDS.add(new Hand());
    }

    public boolean isReady() {
        return HANDS.stream().allMatch(hand -> hand.getAction() != Actions.NONE || hand.getStatus() != Status.PLAYING);
    }

    void addHand(@Nonnull Hand hand) {
        HANDS.add(hand);
    }

    @Nonnull
    public List<Hand> getHands() {
        return new ArrayList<>(HANDS);
    }

}
