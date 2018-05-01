package com.github.drsmugleaf.blackjack;

import com.github.drsmugleaf.blackjack.decks.Cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Player {

    final long ID;

    final List<Cards> HAND = new ArrayList<>();

    Player(long id) {
        ID = id;
    }

}
