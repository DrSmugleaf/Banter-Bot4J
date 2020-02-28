package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.GuildMusicManager;
import com.github.drsmugleaf.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(
        tags = {Tags.DELETE_COMMAND_MESSAGE},
        description = "The current queue of tracks"
)
public class Queue extends MusicCommand {

    @Override
    public void run() {
        Optional<TrackScheduler> schedulerOptional = EVENT
                .getGuildId()
                .map(Music::getGuildMusicManager)
                .map(GuildMusicManager::getScheduler);

        Optional<Member> memberOptional = EVENT.getMember();

        if (schedulerOptional.isPresent() && memberOptional.isPresent()) {
            TrackScheduler scheduler = schedulerOptional.get();
            Member author = memberOptional.get();
            AudioTrack track = scheduler.getCurrentTrack();
            if (track == null) {
                reply("There are no tracks currently playing or in the queue.").subscribe();
                return;
            }

            TimeUnit msUnit = TimeUnit.MILLISECONDS;
            long trackDuration = track.getDuration();
            String trackDurationString = String.format(
                    "%02d:%02d:%02d",
                    msUnit.toHours(trackDuration) % 24,
                    msUnit.toMinutes(trackDuration) % 60,
                    msUnit.toSeconds(trackDuration) % 60
            );

            long trackTimeLeft = trackDuration - track.getPosition();
            String trackTimeLeftString = String.format(
                    "%02d:%02d:%02d",
                    msUnit.toHours(trackTimeLeft) % 24,
                    msUnit.toMinutes(trackTimeLeft) % 60,
                    msUnit.toSeconds(trackTimeLeft) % 60
            );

            String trackStatus = scheduler.isPaused() ? "Paused" : "Playing";

            List<AudioTrack> queue = scheduler.getQueue();
            reply(message -> message.setEmbed(embed -> {
                embed
                        .setAuthor(author.getDisplayName(), null, author.getAvatarUrl())
                        .setTitle("Currently playing: " + track.getInfo().title)
                        .setDescription("\n" +
                                "Track status: " + trackStatus + "\n" +
                                "Track duration: " + trackDurationString + "\n" +
                                "Time remaining: " + trackTimeLeftString
                        )
                        .addField("Tracks in queue", String.valueOf(queue.size()), false);

                if (scheduler.hasNextTrack()) {
                    long queueDuration = 0;
                    for (AudioTrack queuedTrack : queue) {
                        queueDuration += queuedTrack.getDuration();
                    }
                    String queueDurationString = String.format(
                            "%02d:%02d:%02d",
                            msUnit.toHours(queueDuration) % 24,
                            msUnit.toMinutes(queueDuration) % 60,
                            msUnit.toSeconds(queueDuration) % 60
                    );

                    embed.addField("Queue duration", queueDurationString, false);
                }
            })).subscribe();
        }
    }

}
