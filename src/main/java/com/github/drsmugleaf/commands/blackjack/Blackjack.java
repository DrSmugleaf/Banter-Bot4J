package com.github.drsmugleaf.commands.blackjack;

import com.github.drsmugleaf.blackjack.Game;
import com.github.drsmugleaf.blackjack.GameBuilder;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 28/06/2018
 */
@CommandInfo(aliases = {"juan"})
public class Blackjack extends Command {

    private final String BLACKJACK_CHANNEL_NAME = "bb-blackjack";

    static final BiMap<IChannel, Game> GAMES = HashBiMap.create();

    protected Blackjack(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        List<IChannel> channels = guild.getChannelsByName(BLACKJACK_CHANNEL_NAME);
        IChannel blackjackChannel;
        if (channels.isEmpty()) {
            try {
                blackjackChannel = guild.createChannel(BLACKJACK_CHANNEL_NAME);
                GAMES.put(blackjackChannel, GameBuilder.buildDefault());
            } catch (MissingPermissionsException e) {
                String missingPermissions = e.getMissingPermissions().stream().map(Permissions::name).collect(Collectors.joining(", "));
                event.reply(
                        "I don't have permission to create a channel to play blackjack in.\n" +
                        "Missing permissions: " + missingPermissions
                );
                return;
            }
        } else {
            blackjackChannel = channels.get(0);
        }

        GAMES.putIfAbsent(blackjackChannel, GameBuilder.buildDefault());

        Game game = GAMES.get(blackjackChannel);
        Long authorID = event.getAuthor().getLongID();
        if (game.hasPlayer(authorID)) {
            event.reply("Removed you from the Blackjack game.");
        }

        game.addPlayer(authorID);

        String response = "Added you to a Blackjack game in " + blackjackChannel.getName();
        event.reply(response);
    }

}
