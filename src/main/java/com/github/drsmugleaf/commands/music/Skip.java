package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import com.github.drsmugleaf.youtube.GuildMusicManager;
import com.github.drsmugleaf.youtube.TrackUserData;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
public class Skip extends Command {

    @Nonnull
    private static final Map<IGuild, List<IUser>> SKIP_VOTES = new HashMap<>();

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IGuild guild = event.getGuild();

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        AudioTrack currentTrack = musicManager.getScheduler().getCurrentTrack();
        if (currentTrack == null) {
            event.reply("There isn't a track currently playing.");
            return;
        }

        SKIP_VOTES.computeIfAbsent(guild, k -> new ArrayList<>());

        IUser author = event.getAuthor();
        if (SKIP_VOTES.get(guild).contains(author)) {
            event.reply("You have already voted to skip this track.");
            return;
        }

        SKIP_VOTES.get(guild).add(author);

        IUser bot = event.getClient().getOurUser();
        IChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();
        List<IUser> users = botVoiceChannel.getUsersHere();
        int humanUsers = 0;
        for (IUser user : users) {
            if (!user.isBot()) {
                humanUsers++;
            }
        }

        int votes = SKIP_VOTES.get(guild).size();
        double requiredVotes = humanUsers / 2.0;

        TrackUserData trackUserData = currentTrack.getUserData(TrackUserData.class);
        if (votes >= requiredVotes || author == trackUserData.SUBMITTER) {
            SKIP_VOTES.get(guild).clear();
            musicManager.getScheduler().skip();
            event.reply("Skipped the current track.");
        } else {
            String response = String.format("Votes: %d/%.0f", votes, requiredVotes);
            event.reply(response);
        }
    }

}
