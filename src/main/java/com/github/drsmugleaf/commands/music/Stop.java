package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.*;
import com.github.drsmugleaf.youtube.GuildMusicManager;
import com.github.drsmugleaf.youtube.TrackScheduler;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.AbstractMap;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Stop extends Command {

    protected Stop(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IUser author = event.getAuthor();

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentTrack() == null) {
            event.reply("There aren't any tracks currently playing or in the queue.");
            return;
        }

        TrackScheduler scheduler = Music.getGuildMusicManager(guild).getScheduler();

        AbstractMap.SimpleEntry<IGuild, IUser> pair = new AbstractMap.SimpleEntry<>(guild, author);
        Music.UNDO_STOP_CACHE.put(pair, scheduler.cloneTracks());

        scheduler.stop();
        event.reply(
                "Stopped and removed all tracks from the queue.\n" +
                "You have one minute to restore them back to the queue using " + botPrefix + "undostop."
        );
    }

}
