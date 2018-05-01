package com.github.drsmugleaf.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Player {

    final long ID;

    final List<Cards> HAND = new ArrayList<>();

    Player(long id, List<Cards> hand) {
        ID = id;
        HAND.addAll(hand);
    }

}
