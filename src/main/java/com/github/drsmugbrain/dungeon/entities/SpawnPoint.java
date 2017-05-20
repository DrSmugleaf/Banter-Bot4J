package com.github.drsmugbrain.dungeon.entities;


/**
 * Created by Brian on 20/05/2017.
 */
public class SpawnPoint implements IEntity {
    public int pos_x;
    public int pos_y;

    public SpawnPoint(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
    }

    @Override
    public void interactWith(IEntity other) {}

    @Override
    public void receiveInteraction(IEntity other) {}

    @Override
    public String getCharacter() {
        return "";
    }

    @Override
    public int getX() {
        return this.pos_x;
    }

    @Override
    public int getY() {
        return this.pos_y;
    }

}
