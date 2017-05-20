package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;
import com.github.drsmugbrain.dungeon.helpers.Location;

/**
 * Created by Brian on 16/05/2017.
 */
public class Player implements Character {
    public int pos_x;
    public int pos_y;
    private DungeonMap map;

    private int gold;

    public Player(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
        this.gold = 0;
    }

    @Override
    public boolean moveRight(DungeonMap map){
        if(map.getTile(this.pos_x+1, this.pos_y).canMoveTo()){
            this.pos_x += 1;
            return true;
        }
        return false;
    }

    @Override
    public String getCharacter() {
        return "@";
    }

    public boolean moveLeft(DungeonMap map){
        if(map.getTile(this.pos_x-1, this.pos_y).canMoveTo()){
            this.pos_x -= 1;
            return true;
        }
        return false;
    }

    @Override
    public long getLocation() {
        return Location.buildKey(pos_x, pos_y);
    }

    public boolean moveUp(DungeonMap map){
        if(map.getTile(this.pos_x, this.pos_y-1).canMoveTo()){
            this.pos_y -= 1;
            return true;
        }
        return false;
    }

    public boolean moveDown(DungeonMap map){
        if(map.getTile(this.pos_x, this.pos_y+1).canMoveTo()){
            this.pos_y += 1;
            return true;
        }
        return false;
    }

    @Override
    public void interactWith(IEntity other) {}

    @Override
    public void receiveInteraction(IEntity other) {}

}
