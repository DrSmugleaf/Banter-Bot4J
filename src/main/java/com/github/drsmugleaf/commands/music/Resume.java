package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.util.Permission;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(
        permissions = {Permission.MUTE_MEMBERS},
        tags = {
                Tags.GUILD_ONLY,
                Tags.VOICE_ONLY,
                Tags.SAME_VOICE_CHANNEL,
                Tags.DELETE_COMMAND_MESSAGE
        },
        description = "Resume the currently paused track"
)
public class Resume extends MusicCommand {

    @Override
    public void run() {
        EVENT
                .getGuild()
                .map(Guild::getId)
                .map(Music::getGuildMusicManager)
                .subscribe(manager -> {
                    if (!manager.getScheduler().isPlaying()) {
                        reply("There isn't a track currently playing.").subscribe();
                        return;
                    }

                    if (!manager.getScheduler().isPaused()) {
                        reply("There isn't a track currently paused.").subscribe();
                        return;
                    }

                    manager.getScheduler().resume();
                    reply("Resumed the current track.").subscribe();
                });
    }

}
