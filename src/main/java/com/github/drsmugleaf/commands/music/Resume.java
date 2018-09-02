package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.youtube.GuildMusicManager;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.VOICE_ONLY, Tags.SAME_VOICE_CHANNEL, Tags.DELETE_COMMAND_MESSAGE})
public class Resume extends MusicCommand {

    protected Resume(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();
        IChannel channel = EVENT.getChannel();

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        if (!musicManager.getScheduler().isPlaying()) {
            EVENT.reply("There isn't a track currently playing.");
            return;
        }

        if (!musicManager.getScheduler().isPaused()) {
            EVENT.reply("There isn't a track currently paused.");
            return;
        }

        musicManager.getScheduler().resume();
        EVENT.reply("Resumed the current track.");
    }

}
