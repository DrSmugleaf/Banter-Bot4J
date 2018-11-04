package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.music.GuildMusicManager;
import com.github.drsmugleaf.music.TrackScheduler;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 02/11/2018
 */
@CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY})
public class Leave extends MusicCommand {

    protected Leave(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();
        IVoiceChannel voiceChannel = guild.getConnectedVoiceChannel();
        if (voiceChannel != null) {
            voiceChannel.leave();
        }

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        TrackScheduler scheduler = musicManager.getScheduler();

        scheduler.stop();
    }

}
