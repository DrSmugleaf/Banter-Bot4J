package com.github.drsmugbrain.dungeon.entities;

import com.github.drsmugbrain.dungeon.DungeonMap;

/**
 * Created by Brian on 20/05/2017.
 */
public interface IEntity {
    public void interactWith(IEntity other);
    public void receiveInteraction(IEntity other);

    public String getCharacter();
}
