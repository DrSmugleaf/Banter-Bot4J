package com.github.drsmugbrain.dungeon.entities;

import java.util.Random;

/**
 * Created by Brian on 20/05/2017.
 */
public class Treasure implements IEntity {
    private int gold;
    public int pos_x;
    public int pos_Y;

    public Treasure(int x, int y, int gold){
        this.gold = gold;
    }

    public Treasure(int x, int y){
        this(x, y, new Random().nextInt(99)+1);
    }

    @Override
    public void interactWith(IEntity other) {}

    @Override
    public void receiveInteraction(IEntity other) {
        if(other instanceof Player){

        }
    }

    @Override
    public String getCharacter() {
        return null;
    }

    @Override
    public long getLocation() {
        return 0;
    }
}
