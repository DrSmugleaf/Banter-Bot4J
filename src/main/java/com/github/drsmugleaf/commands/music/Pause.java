package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
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
        description = "Pause the currently playing track"
)
public class Pause extends MusicCommand {

    @Override
    public void run() {
        EVENT
                .getGuildId()
                .map(Music::getGuildMusicManager)
                .ifPresent(manager -> {
                    if (!manager.getScheduler().isPlaying()) {
                        reply("There isn't a track currently playing.").subscribe();
                        return;
                    }

                    if (manager.getScheduler().isPaused()) {
                        reply("The current track is already paused. Use " + BanterBot4J.BOT_PREFIX + "resume to resume it.").subscribe();
                        return;
                    }

                    manager.getScheduler().pause();
                    reply("Paused the current track.").subscribe();
                });
    }

}
