package com.github.drsmugbrain.mafia.chat;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class Chat {

    private final List<Channel> CHANNELS = new ArrayList<>();

    public Chat() {}

    private void createChannel(@Nonnull List<Chatter> chatters) {
        Channel channel = new Channel(chatters);
        this.CHANNELS.add(channel);
    }

    public void createChannel(@Nonnull Player... players) {
        this.createChannel(Chatter.toChatters(players));
    }

    public void createChannel(@Nonnull Collection<Player> players) {
        this.createChannel(Chatter.toChatters(players));
    }

    @Nonnull
    public List<Channel> getChannels() {
        return this.CHANNELS;
    }

    public void deleteChannel(@Nonnull Channel channel) {
        this.CHANNELS.remove(channel);
    }

    public void deleteChannel(int index) {
        this.deleteChannel(this.CHANNELS.get(index));
    }

    public void sendMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull String message) {
        for (Channel channel : this.CHANNELS) {
            channel.sendMessage(game, player, message);
        }
    }

}
