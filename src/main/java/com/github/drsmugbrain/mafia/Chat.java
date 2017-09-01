package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.events.ChatEvent;
import com.github.drsmugbrain.mafia.events.EventDispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class Chat {

    private final List<Chatter> CHATTERS = new ArrayList<>();

    protected Chat(Chatter... chatters) {
        Collections.addAll(this.CHATTERS, chatters);
    }

    protected void addPlayers(Chatter... chatters) {
        Collections.addAll(this.CHATTERS, chatters);
    }

    public List<Chatter> getPlayers() {
        return this.CHATTERS;
    }

    protected void setPlayers(Chatter... chatters) {
        this.CHATTERS.clear();
        this.addPlayers(chatters);
    }

    protected void sendMessage(Game game, Chatter sender, String message) {
        for (Chatter recipient : this.CHATTERS) {
            ChatEvent event = new ChatEvent(game, recipient, sender, message);
            EventDispatcher.dispatch(event);
        }
    }

}
