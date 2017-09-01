package com.github.drsmugbrain.mafia.chat;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class Chat {

    private final List<Channel> CHANNELS = new ArrayList<>();

    public Chat() {}

    private void createChannel(List<Chatter> chatters) {
        Channel channel = new Channel(chatters);
        this.CHANNELS.add(channel);
    }

    public void createChannel(Player... players) {
        this.createChannel(Chatter.toChatters(players));
    }

    public void createChannel(Collection<Player> players) {
        this.createChannel(Chatter.toChatters(players));
    }

    public List<Channel> getChannels() {
        return this.CHANNELS;
    }

    public void deleteChannel(Channel channel) {
        this.CHANNELS.remove(channel);
    }

    public void deleteChannel(int index) {
        this.deleteChannel(this.CHANNELS.get(index));
    }

    public void sendMessage(Game game, Player player, String message) {
        for (Channel channel : this.CHANNELS) {
            channel.sendMessage(game, player, message);
        }
    }

}
