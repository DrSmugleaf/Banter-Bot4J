package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;

import java.util.List;

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


    public void addGold(int gold){
        this.gold += gold;
        System.out.println("You have " + this.gold + " gold");
    }


    public void actLeft(DungeonMap map){
        if(this.moveLeft(map)){
            return;
        }
        List<IEntity> entities = map.getEntitiesFromCoords(this.pos_x-1, this.pos_y);
        this.interactWith(entities);
    }


    public void actRight(DungeonMap map){
        if(this.moveRight(map)){
            return;
        }
        List<IEntity> entities = map.getEntitiesFromCoords(this.pos_x+1, this.pos_y);
        this.interactWith(entities);
    }


    public void actUp(DungeonMap map){
        if(this.moveUp(map)){
            return;
        }
        List<IEntity> entities = map.getEntitiesFromCoords(this.pos_x, this.pos_y-1);
        this.interactWith(entities);
    }


    public void actDown(DungeonMap map){
        if(this.moveDown(map)){
            return;
        }
        List<IEntity> entities = map.getEntitiesFromCoords(this.pos_x, this.pos_y+1);
        this.interactWith(entities);
    }


    @Override
    public String getCharacter() {
        return "@";
    }

    @Override
    public int getX() {
        return this.pos_x;
    }

    @Override
    public int getY() {
        return this.pos_y;
    }


    @Override
    public boolean moveLeft(DungeonMap map){
        if(map.canMoveTo(this.pos_x-1, this.pos_y)){
            this.pos_x -= 1;
            return true;
        }
        return false;
    }


    @Override
    public boolean moveRight(DungeonMap map){
        if(map.canMoveTo(this.pos_x+1, this.pos_y)){
            this.pos_x += 1;
            return true;
        }
        return false;
    }


    @Override
    public boolean moveUp(DungeonMap map){
        if(map.canMoveTo(this.pos_x, this.pos_y-1)){
            this.pos_y -= 1;
            return true;
        }
        return false;
    }


    @Override
    public boolean moveDown(DungeonMap map){
        if(map.canMoveTo(this.pos_x, this.pos_y+1)){
            this.pos_y += 1;
            return true;
        }
        return false;
    }


    @Override
    public void interactWith(List<IEntity> others) {
        if(others.size() > 0){
            others.forEach(entity -> entity.receiveInteraction(this));
        }
    }

    @Override
    public void receiveInteraction(IEntity other) {}

}
