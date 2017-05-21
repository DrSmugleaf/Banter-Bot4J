package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;

import java.util.List;

/**
 * Created by Brian on 20/05/2017.
 */
public interface IEntity {
    void interactWith(List<IEntity> other);
    void receiveInteraction(IEntity other);

    String getCharacter();

    int getX();
    int getY();
    boolean isSolid();
}
