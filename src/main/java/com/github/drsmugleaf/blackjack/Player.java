package com.github.drsmugleaf.blackjack;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
public class Player {

    final long ID;

    final Hand HAND = new Hand();

    Player(long id) {
        ID = id;
    }

}
