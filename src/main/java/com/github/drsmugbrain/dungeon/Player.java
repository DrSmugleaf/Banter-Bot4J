package com.github.drsmugbrain.dungeon;

/**
 * Created by Brian on 16/05/2017.
 */
public class Player {
    private int pos_x;
    private int pos_y;

    Player(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
    }

    public boolean moveRight(DungeonMap map){
        if(map.getTile(this.pos_x+1, this.pos_y).canMoveTo()){
            this.pos_x += 1;
            return true;
        }
        return false;
    }

    public boolean moveLeft(DungeonMap map){
        if(map.getTile(this.pos_x-1, this.pos_y).canMoveTo()){
            this.pos_x -= 1;
            return true;
        }
        return false;
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

}
