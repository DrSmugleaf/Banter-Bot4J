package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.List;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(
        aliases = {"undostop"},
        tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE},
        description = "Restore the stopped tracks back to the queue"
)
public class Restore extends MusicCommand {

    @Override
    public void run() {
        EVENT
                .getGuild()
                .zipWith(Mono.justOrEmpty(EVENT.getMessage().getAuthor()))
                .map(tuple -> new AbstractMap.SimpleEntry<>(tuple.getT1(), tuple.getT2()))
                .subscribe(entry -> {
                    List<AudioTrack> tracks = Music.UNDO_STOP_CACHE.getIfPresent(entry);

                    if (tracks == null) {
                        reply("You haven't stopped any tracks in the last minute.").subscribe();
                        return;
                    }

                    Music.UNDO_STOP_CACHE.invalidate(entry);
                    TrackScheduler scheduler = Music.getGuildMusicManager(entry.getKey().getId()).getScheduler();
                    tracks.addAll(scheduler.cloneTracks());
                    scheduler.stop();
                    scheduler.queue(tracks);

                    reply("Restored all stopped tracks.").subscribe();
                });
    }

}
