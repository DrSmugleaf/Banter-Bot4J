package com.github.drsmugleaf.commands.blackjack;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.blackjack.*;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 29/06/2018
 */
public class Events {

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

        player.setAction(message);
    }

    @BlackjackEventHandler(event = StartEvent.class)
    public static void handle(@Nonnull StartEvent event) {
        StringBuilder response = new StringBuilder();
        Game game = event.GAME;

        response.append(game.getDealer());

        IChannel channel = Blackjack.GAMES.inverse().get(game);
        game.getPlayers().forEach((id, player) -> {
            IUser user = BanterBot4J.CLIENT.fetchUser(id);
            IGuild guild = channel.getGuild();
            String username = user.getDisplayName(guild);

            response
                    .append(username)
                    .append("'s hand: ")
                    .append(player);
        });

        channel.sendMessage(response.toString());
    }

}
