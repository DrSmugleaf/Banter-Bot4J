package com.github.drsmugbrain.mafia.chat;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;
import com.github.drsmugbrain.mafia.events.ChatEvent;
import com.github.drsmugbrain.mafia.events.EventDispatcher;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public class Channel {

    private final Map<Long, Chatter> CHATTERS = new HashMap<>();
    private final Type TYPE;

    protected Channel(@Nonnull Type type, @Nonnull Chatter... chatters) {
        for (Chatter chatter : chatters) {
            this.CHATTERS.put(chatter.getID(), chatter);
        }
        this.TYPE = type;
    }

    protected Channel(Type type, @Nonnull Collection<Chatter> chatters) {
        this(type, chatters.toArray(new Chatter[chatters.size()]));
    }

    protected void addChatters(@Nonnull Chatter... chatters) {
        for (Chatter chatter : chatters) {
            if (this.CHATTERS.containsValue(chatter)) {
                continue;
            }
            this.CHATTERS.put(chatter.getID(), chatter);
        }
    }

    @Nonnull
    public Map<Long, Chatter> getChatters() {
        return this.CHATTERS;
    }

    protected void removeChatters(@Nonnull Long... ids) {
        for (Long id : ids) {
            this.CHATTERS.remove(id);
        }
    }

    protected void removeChatters(@Nonnull Chatter... chatters) {
        for (Chatter chatter : chatters) {
            this.removeChatters(chatter.getID());
        }
    }

    protected void setChatters(@Nonnull Chatter... chatters) {
        this.CHATTERS.clear();
        this.addChatters(chatters);
    }

    protected void sendMessage(@Nonnull Game game, @Nonnull Chatter sender, @Nonnull String message) {
        List<Chatter> chatters = new ArrayList<>(this.CHATTERS.values());
        chatters.remove(sender);

        for (Chatter recipient : chatters) {
            ChatEvent event = new ChatEvent(game, recipient, sender, message);
            EventDispatcher.dispatch(event);
        }
    }

    protected void sendMessage(@Nonnull Game game, @Nonnull Player sender, @Nonnull String message) {
        this.sendMessage(game, this.CHATTERS.get(sender.getID()), message);
    }

    public Type getType() {
        return this.TYPE;
    }

}
