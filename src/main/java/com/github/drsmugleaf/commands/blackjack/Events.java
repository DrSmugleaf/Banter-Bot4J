package com.github.drsmugleaf.commands.blackjack;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.blackjack.*;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/06/2018
 */
public class Events {

    @Nonnull
    private static String parseHand(@Nonnull Game game, @Nonnull Player player) {
        StringBuilder response = new StringBuilder();
        IChannel channel = Blackjack.GAMES.inverse().get(game);
        IUser user = BanterBot4J.CLIENT.fetchUser(player.ID);
        IGuild guild = channel.getGuild();
        String username = user.getDisplayName(guild);

        response
                .append(username)
                .append(" hand: ")
                .append(player)
                .append("\n");

        return response.toString();
    }

    @Nonnull
    private static String parseHands(@Nonnull Game game) {
        StringBuilder response = new StringBuilder();

        response
                .append(game.getDealer())
                .append("\n");

        for (Player player : game.getPlayers().values()) {
            String playerHand = parseHand(game, player);
            response.append(playerHand);
        }

        return response.toString();
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        IChannel channel = event.getChannel();
        Game game = Blackjack.GAMES.get(channel);
        Long authorID = event.getAuthor().getLongID();
        if (game == null || !game.hasPlayer(authorID)) {
            return;
        }

        String message = event.getMessage().getContent();
        Player player = game.getPlayer(authorID);
        if (player.getStatus() == Status.WAITING) {
            String authorName = event.getAuthor().getDisplayName(event.getGuild());
            CommandReceivedEvent.sendMessage(
                    event.getChannel(),
                    authorName + ", you aren't playing yet, wait until this round finishes."
            );
            return;
        }

        game.setAction(player, message);
    }

    @BlackjackEventHandler(event = EndEvent.class)
    public static void handle(@Nonnull EndEvent event) {
        Game game = event.GAME;
        IChannel channel = Blackjack.GAMES.inverse().get(game);

        Blackjack.GAMES.remove(channel);
        try {
            channel.delete();
        } catch (MissingPermissionsException ignored) {}
    }

    @BlackjackEventHandler(event = EndRoundEvent.class)
    public static void handle(@Nonnull EndRoundEvent event) {
        Game game = event.GAME;
        String response = parseHands(game);
        IChannel channel = Blackjack.GAMES.inverse().get(game);

        CommandReceivedEvent.sendMessage(channel, response);
    }

    @BlackjackEventHandler(event = LoseEvent.class)
    public static void handle(@Nonnull LoseEvent event) {
        IUser user = BanterBot4J.CLIENT.fetchUser(event.PLAYER.ID);
        Game game = event.GAME;
        IChannel channel = Blackjack.GAMES.inverse().get(game);
        IGuild guild = channel.getGuild();
        String username = user.getDisplayName(guild);

        StringBuilder response = new StringBuilder();
        response
                .append(username)
                .append(" loses.")
                .append("\n");

        Player player = event.PLAYER;
        String playerHand = parseHand(game, player);
        response.append(playerHand);

        Integer score = event.PLAYER.HAND.getScore();
        response
                .append("Total: ")
                .append(score)
                .append(".");

        CommandReceivedEvent.sendMessage(channel, response.toString());
    }

    @BlackjackEventHandler(event = StartEvent.class)
    public static void handle(@Nonnull StartEvent event) {
        Game game = event.GAME;
        String response = parseHands(game);
        IChannel channel = Blackjack.GAMES.inverse().get(game);

        CommandReceivedEvent.sendMessage(channel, response);
    }

    @BlackjackEventHandler(event = SurrenderEvent.class)
    public static void handle(@Nonnull SurrenderEvent event) {

    }

    @BlackjackEventHandler(event = TieEvent.class)
    public static void handle(@Nonnull TieEvent event) {

    }

    @BlackjackEventHandler(event = TurnStartEvent.class)
    public static void handle(@Nonnull TurnStartEvent event) {

    }

    @BlackjackEventHandler(event = WinEvent.class)
    public static void handle(@Nonnull WinEvent event) {

    }

}
