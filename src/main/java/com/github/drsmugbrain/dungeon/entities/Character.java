package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;
import com.github.drsmugbrain.dungeon.helpers.Location;

/**
 * Created by Brian on 20/05/2017.
 */
public interface Character extends IEntity {
    public boolean moveUp(DungeonMap map);
    public boolean moveDown(DungeonMap map);
    public boolean moveLeft(DungeonMap map);
    public boolean moveRight(DungeonMap map);
}
