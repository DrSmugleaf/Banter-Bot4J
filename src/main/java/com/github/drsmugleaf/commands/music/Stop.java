package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.GuildMusicManager;
import com.github.drsmugleaf.music.TrackScheduler;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(
        permissions = {Permission.MUTE_MEMBERS},
        tags = {Tags.DELETE_COMMAND_MESSAGE},
        description = "Stop the current track"
)
public class Stop extends MusicCommand {

    @Override
    public void run() {
        EVENT
                .getGuild()
                .zipWith(Mono.justOrEmpty(EVENT.getMember()))
                .subscribe(tuple -> {
                    Guild guild = tuple.getT1();
                    Member author = tuple.getT2();

                    GuildMusicManager musicManager = Music.getGuildMusicManager(guild.getId());
                    TrackScheduler scheduler = musicManager.getScheduler();
                    if (scheduler.getCurrentTrack() == null) {
                        reply("There aren't any tracks currently playing or in the queue.").subscribe();
                        return;
                    }

                    AbstractMap.SimpleEntry<Guild, Member> pair = new AbstractMap.SimpleEntry<>(guild, author);
                    Music.UNDO_STOP_CACHE.put(pair, scheduler.cloneTracks());

                    scheduler.stop();
                    reply(
                            "Stopped and removed all tracks from the queue.\n" +
                                    "You have one minute to restore them back to the queue using " + BanterBot4J.BOT_PREFIX + "restore."
                    ).subscribe();
                });
    }

}
