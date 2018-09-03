package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.GuildMusicManager;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
public class Pause extends MusicCommand {

    protected Pause(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            EVENT.reply("There isn't a track currently playing.");
            return;
        }

        if (musicManager.getScheduler().isPaused()) {
            EVENT.reply("The current track is already paused. Use " + BanterBot4J.BOT_PREFIX + "resume to resume it.");
            return;
        }

        musicManager.getScheduler().pause();
        EVENT.reply("Paused the current track.");
    }

}
