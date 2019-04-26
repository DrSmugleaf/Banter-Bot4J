package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Queue extends MusicCommand {

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();
        IUser author = EVENT.getAuthor();

        TrackScheduler scheduler = Music.getGuildMusicManager(guild).getScheduler();
        AudioTrack currentTrack = scheduler.getCurrentTrack();
        if (currentTrack == null) {
            EVENT.reply("There are no tracks currently playing or in the queue.");
            return;
        }

        TimeUnit msUnit = TimeUnit.MILLISECONDS;
        long trackDuration = currentTrack.getDuration();
        String trackDurationString = String.format(
                "%02d:%02d:%02d",
                msUnit.toHours(trackDuration) % 24,
                msUnit.toMinutes(trackDuration) % 60,
                msUnit.toSeconds(trackDuration) % 60
        );

        long trackTimeLeft = trackDuration - currentTrack.getPosition();
        String trackTimeLeftString = String.format(
                "%02d:%02d:%02d",
                msUnit.toHours(trackTimeLeft) % 24,
                msUnit.toMinutes(trackTimeLeft) % 60,
                msUnit.toSeconds(trackTimeLeft) % 60
        );

        String trackStatus = "Playing";
        if (scheduler.isPaused()) {
            trackStatus = "Paused";
        }

        List<AudioTrack> queue = scheduler.getQueue();
        EmbedBuilder builder = new EmbedBuilder();
        builder.withAuthorName(author.getDisplayName(guild))
                .withAuthorIcon(author.getAvatarURL())
                .withTitle("Currently playing: " + currentTrack.getInfo().title)
                .appendDescription("\n")
                .appendDescription("Track status: " + trackStatus)
                .appendDescription("\n")
                .appendDescription("Track duration: " + trackDurationString)
                .appendDescription("\n")
                .appendDescription("Time remaining: " + trackTimeLeftString)
                .appendField("Tracks in queue", String.valueOf(queue.size()), false);

        if (scheduler.hasNextTrack()) {
            long queueDuration = 0;
            for (AudioTrack track : queue) {
                queueDuration += track.getDuration();
            }
            String queueDurationString = String.format(
                    "%02d:%02d:%02d",
                    msUnit.toHours(queueDuration) % 24,
                    msUnit.toMinutes(queueDuration) % 60,
                    msUnit.toSeconds(queueDuration) % 60
            );

            builder.appendField("Queue duration", queueDurationString, false);
        }

        EVENT.reply(builder.build());
    }

}
