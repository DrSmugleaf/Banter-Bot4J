package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.AudioResultHandler;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Play extends MusicCommand {

    protected Play(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        IChannel channel = EVENT.getChannel();

        IUser author = EVENT.getAuthor();
        String searchString = String.join(" ", ARGS);

        Music.PLAYER_MANAGER.loadItem(searchString, new AudioResultHandler(channel, author, searchString));
    }

}
