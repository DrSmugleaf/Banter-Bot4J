package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.*;
import com.github.drsmugleaf.youtube.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.List;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Undostop extends Command {

    protected Undostop(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();

        IUser author = event.getAuthor();
        AbstractMap.SimpleEntry<IGuild, IUser> pair = new AbstractMap.SimpleEntry<>(guild, author);
        List<AudioTrack> tracks = Music.UNDO_STOP_CACHE.getIfPresent(pair);
        if (tracks == null) {
            event.reply("You haven't stopped any tracks in the last minute.");
            return;
        }

        Music.UNDO_STOP_CACHE.invalidate(pair);

        TrackScheduler scheduler = Music.getGuildMusicManager(guild).getScheduler();
        tracks.addAll(scheduler.cloneTracks());
        scheduler.stop();
        scheduler.queue(tracks);
        event.reply("Restored all stopped tracks.");
    }

}
