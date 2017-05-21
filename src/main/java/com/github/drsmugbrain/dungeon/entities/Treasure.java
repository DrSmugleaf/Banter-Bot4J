package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;

import java.util.List;
import java.util.Random;

/**
 * Created by Brian on 20/05/2017.
 */
public class Treasure implements IEntity {
    private int gold;
    private DungeonMap map;
    public int pos_x;
    public int pos_y;

    public Treasure(int x, int y, DungeonMap map, int gold){
        this.gold = gold;
        this.pos_x = x;
        this.pos_y = y;
        this.map = map;
    }

    public Treasure(int x, int y, DungeonMap map){
        this(x, y, map, new Random().nextInt(100)+1);
    }

    @Override
    public void interactWith(List<IEntity> others) {}

    @Override
    public void receiveInteraction(IEntity other) {
        System.out.println("interaction received");
        if(other instanceof Player){
            System.out.println("Algo raro pasa");
            Player player = (Player) other;
            player.addGold(this.gold);
            this.map.removeEntity(this);
        }else{
            System.out.println("nope");
        }
    }

    @Override
    public String getCharacter() {
        return "O";
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
    public boolean isSolid() {
        return true;
    }

}
