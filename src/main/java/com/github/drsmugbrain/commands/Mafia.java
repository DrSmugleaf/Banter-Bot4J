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
    public static void mafia(MessageReceivedEvent event, List<String> args) {}

    @Command
    public static void mafiadev(MessageReceivedEvent event, List<String> args) {
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
    public static void handle(MessageReceivedEvent event) {
        IUser author = event.getAuthor();
        long authorID = event.getAuthor().getLongID();
        if (!Mafia.GAMES.containsKey(author.getLongID())) {
            return;
        }
        if (!event.getChannel().isPrivate()) {
            return;
        }

        Mafia.GAMES.get(authorID).sendMessage(authorID, event.getMessage().getContent());
    }

    @MafiaEventHandler(event = ChatEvent.class)
    public static void handle(ChatEvent event) {
        IChannel channel = Bot.client.fetchUser(event.getRecipient().getID()).getOrCreatePMChannel();
        Bot.sendMessage(channel, event.getMessage());
    }

    @MafiaEventHandler(event = GameStartEvent.class)
    public static void handle(GameStartEvent event) {
        for (Player player : event.getGame().getPlayers().values()) {
            if (player.isBot()) {
                continue;
            }
            IUser user = Bot.client.fetchUser(player.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage("Your role is " + player.getRole().getBaseRole().getName());
        }
    }

    @MafiaEventHandler(event = DayStartEvent.class)
    public static void handle(DayStartEvent event) {
        for (Player player : event.getGame().getPlayers().values()) {
            if (player.isBot()) {
                continue;
            }
            IUser user = Bot.client.fetchUser(player.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage("Day: " + event.getGame().getCycle().getDay());
        }
    }

    @MafiaEventHandler(event = NightStartEvent.class)
    public static void handle(NightStartEvent event) {
        for (Player player : event.getGame().getPlayers().values()) {
            if (player.isBot()) {
                continue;
            }
            IUser user = Bot.client.fetchUser(player.getID());
            IPrivateChannel channel = user.getOrCreatePMChannel();
            channel.sendMessage("Night: " + event.getGame().getCycle().getDay());
        }
    }

}
