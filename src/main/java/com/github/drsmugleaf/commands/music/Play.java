package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import com.github.drsmugleaf.youtube.AudioResultHandler;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Play extends Command {

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();
        String searchString = String.join(" ", event.ARGS);

        Music.PLAYER_MANAGER.loadItem(searchString, new AudioResultHandler(channel, author, searchString));
    }

}
