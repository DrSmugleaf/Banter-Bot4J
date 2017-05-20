package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;

/**
 * Created by Brian on 20/05/2017.
 */
public interface IEntity {
    void interactWith(IEntity other);
    void receiveInteraction(IEntity other);

    String getCharacter();

    int getX();
    int getY();
}
