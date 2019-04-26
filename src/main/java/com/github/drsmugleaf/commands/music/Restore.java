package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.AbstractMap;
import java.util.List;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(aliases = {"undostop"}, tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Restore extends MusicCommand {

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();
        IUser author = EVENT.getAuthor();
        AbstractMap.SimpleEntry<IGuild, IUser> pair = new AbstractMap.SimpleEntry<>(guild, author);
        List<AudioTrack> tracks = Music.UNDO_STOP_CACHE.getIfPresent(pair);

        if (tracks == null) {
            EVENT.reply("You haven't stopped any tracks in the last minute.");
            return;
        }

        Music.UNDO_STOP_CACHE.invalidate(pair);
        TrackScheduler scheduler = Music.getGuildMusicManager(guild).getScheduler();
        tracks.addAll(scheduler.cloneTracks());
        scheduler.stop();
        scheduler.queue(tracks);

        EVENT.reply("Restored all stopped tracks.");
    }

}
