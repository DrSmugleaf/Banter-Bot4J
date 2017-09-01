package com.github.drsmugbrain.mafia.chat;

import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public class Chatter {

    private final Player PLAYER;
    private String name;
    private boolean anonymous = false;

    protected Chatter(@Nonnull Player player) {
        this.PLAYER = player;
        this.name = player.getName();
    }

    @Nonnull
    protected static List<Chatter> toChatters(@Nonnull Collection<Player> players) {
        List<Chatter> chatters = new ArrayList<>();

        for (Player player : players) {
            if (player.isBot()) {
                continue;
            }
            chatters.add(new Chatter(player));
        }

        return chatters;
    }

    @Nonnull
    protected static List<Chatter> toChatters(@Nonnull Player... players) {
        return Chatter.toChatters(Arrays.asList(players));
    }

    @Nonnull
    public Player getPlayer() {
        return this.PLAYER;
    }

    public long getID() {
        return this.PLAYER.getID();
    }

    @Nonnull
    public String getName() {
        return this.name;
    }

    @Nonnull
    protected void setName(String name) {
        this.name = name;
    }

    public boolean isAnonymous() {
        return this.anonymous;
    }

    protected void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

}
