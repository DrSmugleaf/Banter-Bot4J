package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.mafia.Game;
import com.github.drsmugbrain.mafia.Player;
import com.github.drsmugbrain.mafia.Setup;
import com.github.drsmugbrain.mafia.events.*;
import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 26/08/2017.
 */
public class Mafia {

    private static final Map<Long, Game> GAMES = new HashMap<>();

    static {
        EventDispatcher.registerListener(new Mafia());
    }

    @Command
    public static void mafia(@Nonnull MessageReceivedEvent event, @Nonnull List<String> args) {}

    @Command
    public static void mafiadev(@Nonnull MessageReceivedEvent event, @Nonnull List<String> args) {
        Long authorID = event.getAuthor().getLongID();
        String authorName = event.getAuthor().getName();

        Map<Long, Player> players = new HashMap<>();
        players.put(event.getAuthor().getLongID(), new Player(authorID, authorName));

        Game game = new Game(Setup.random(), players);
        game.fillWithBots();
        Mafia.GAMES.put(authorID, game);
        game.start();
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        long authorID = event.getAuthor().getLongID();
        if (!Mafia.GAMES.containsKey(authorID)) {
            return;
        }
        if (!event.getChannel().isPrivate()) {
            return;
        }

        Mafia.GAMES.get(authorID).sendMessage(authorID, event.getMessage().getContent());
    }

    @MafiaEventHandler(event = ChatEvent.class)
    public static void handle(@Nonnull ChatEvent event) {
        IChannel channel = Bot.fetchUser(event.getRecipient().getID()).getOrCreatePMChannel();
        Bot.sendMessage(channel, event.getFormattedMessage());
    }

    @MafiaEventHandler(event = GameStartEvent.class)
    public static void handle(@Nonnull GameStartEvent event) {
        for (Player player : event.getGame().getHumanPlayers().values()) {
            IUser user = Bot.fetchUser(player.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage("Your role is " + player.getRole().getBaseRole().getName());
        }
    }

    @MafiaEventHandler(event = DayStartEvent.class)
    public static void handle(@Nonnull DayStartEvent event) {
        for (Player player : event.getGame().getHumanPlayers().values()) {
            IUser user = Bot.fetchUser(player.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage("Day: " + event.getGame().getDay());
        }
    }

    @MafiaEventHandler(event = NightStartEvent.class)
    public static void handle(@Nonnull NightStartEvent event) {
        for (Player player : event.getGame().getHumanPlayers().values()) {
            IUser user = Bot.fetchUser(player.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage("Night: " + event.getGame().getDay());
        }
    }

}
