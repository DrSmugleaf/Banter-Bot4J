package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.GuildMusicManager;
import com.github.drsmugleaf.music.TrackUserData;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.VoiceChannel;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
public class Skip extends MusicCommand {

    @Nonnull
    private static final Map<Guild, List<User>> SKIP_VOTES = new HashMap<>();

    @Override
    public void run() {
        Guild guild = EVENT
                .getGuild()
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Couldn't get the message's guild. Message: " + EVENT.getMessage()));

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild.getId());
        AudioTrack currentTrack = musicManager.getScheduler().getCurrentTrack();
        if (currentTrack == null) {
            reply("There isn't a track currently playing.").subscribe();
            return;
        }

        SKIP_VOTES.computeIfAbsent(guild, k -> new ArrayList<>());

        EVENT.getMessage().getAuthor().ifPresent(author -> {
            if (SKIP_VOTES.get(guild).contains(author)) {
                reply("You have already voted to skip this track.").subscribe();
                return;
            }

            SKIP_VOTES.get(guild).add(author);

            int votes = SKIP_VOTES.get(guild).size();
            EVENT
                    .getClient()
                    .getSelf()
                    .zipWith(EVENT.getGuild())
                    .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2().getId()))
                    .flatMap(Member::getVoiceState)
                    .flatMap(VoiceState::getChannel)
                    .flatMapMany(VoiceChannel::getVoiceStates)
                    .filterWhen(state -> state.getUser().map(User::isBot).map(b -> !b))
                    .count()
                    .zipWith(Mono.just(currentTrack.getUserData(TrackUserData.class)))
                    .subscribe(tuple -> {
                        double required = tuple.getT1() / 2.0;

                        if (votes >= required || author == tuple.getT2().SUBMITTER) {
                            SKIP_VOTES.get(guild).clear();
                            musicManager.getScheduler().skip();
                            reply("Skipped the current track.").subscribe();
                        } else {
                            String response = String.format("Votes to skip: %d/%.0f", votes, required);
                            reply(response).subscribe();
                        }
                    });
        });
    }

}
