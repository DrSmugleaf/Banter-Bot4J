package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.*;
import com.github.drsmugleaf.youtube.GuildMusicManager;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
public class Pause extends Command {

    protected Pause(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        IGuild guild = event.getGuild();

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            event.reply("There isn't a track currently playing.");
            return;
        }

        if (musicManager.getScheduler().isPaused()) {
            event.reply("The current track is already paused. Use " + BOT_PREFIX + "resume to resume it.");
            return;
        }

        musicManager.getScheduler().pause();
        event.reply("Paused the current track.");
    }

}
