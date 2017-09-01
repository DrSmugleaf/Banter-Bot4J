package com.github.drsmugbrain.mafia.events;

import com.github.drsmugbrain.mafia.chat.Chatter;
import com.github.drsmugbrain.mafia.Game;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public class ChatEvent extends MessageEvent {

    private final Chatter RECIPIENT;
    private final Chatter SENDER;

    public ChatEvent(@Nonnull Game game, @Nonnull Chatter recipient, @Nonnull Chatter sender, @Nonnull String message) {
        super(game, recipient.getPlayer(), message);
        this.RECIPIENT = recipient;
        this.SENDER = sender;
    }

    public Chatter getRecipient() {
        return this.RECIPIENT;
    }

    public Chatter getSender() {
        return this.SENDER;
    }

}
