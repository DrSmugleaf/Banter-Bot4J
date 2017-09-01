package com.github.drsmugbrain.mafia.chat;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public class Chat {

    private final List<Channel> CHANNELS = new ArrayList<>();

    public Chat() {}

    public void createChannel(@Nonnull Type type, @Nonnull List<Chatter> chatters) {
        Channel channel = new Channel(type, chatters);
        this.CHANNELS.add(channel);
    }

    public void createChannel(@Nonnull Type type, @Nonnull Player... players) {
        this.createChannel(type, Chatter.toChatters(players));
    }

    public void createChannel(Type type, @Nonnull Collection<Player> players) {
        this.createChannel(type, Chatter.toChatters(players));
    }

    public void createChannel(Type type, @Nonnull Chatter... chatters) {
        this.createChannel(type, Arrays.asList(chatters));
    }

    @Nonnull
    public List<Channel> getChannels() {
        return this.CHANNELS;
    }

    @Nonnull
    public Channel getChannel(Type type) {
        for (Channel channel : this.CHANNELS) {
            if (channel.getType() == type) {
                return channel;
            }
        }

        throw new NullPointerException("Channel with type " + type + " doesn't exist");
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

    public void addChatter(Channel channel, Chatter... chatters) {
        channel.addChatters(chatters);
        this.CHANNELS.add(channel);
    }

    public void removeChatter(Channel channel, Chatter... chatters) {
        channel.removeChatters(chatters);
    }

    public void removeChatter(Channel channel, Long... chatters) {
        channel.removeChatters(chatters);
    }

}
