package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public class Chatter {

    private final Player PLAYER;
    private String name;
    private boolean anonymous;

    protected Chatter(@Nonnull Player player) {
        this.PLAYER = player;
    }

    public Player getPlayer() {
        return this.PLAYER;
    }

    public long getID() {
        return this.PLAYER.getID();
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    protected void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

}
