package com.github.drsmugleaf.commands.music;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.youtube.GuildMusicManager;
import com.github.drsmugleaf.youtube.TrackScheduler;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.AbstractMap;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
@CommandInfo(permissions = {Permissions.VOICE_MUTE_MEMBERS}, tags = {Tags.GUILD_ONLY, Tags.DELETE_COMMAND_MESSAGE})
public class Stop extends MusicCommand {

    protected Stop(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();
        IUser author = EVENT.getAuthor();

        GuildMusicManager musicManager = Music.getGuildMusicManager(guild);
        if (musicManager.getScheduler().getCurrentTrack() == null) {
            EVENT.reply("There aren't any tracks currently playing or in the queue.");
            return;
        }

        TrackScheduler scheduler = Music.getGuildMusicManager(guild).getScheduler();

        AbstractMap.SimpleEntry<IGuild, IUser> pair = new AbstractMap.SimpleEntry<>(guild, author);
        Music.UNDO_STOP_CACHE.put(pair, scheduler.cloneTracks());

        scheduler.stop();
        EVENT.reply(
                "Stopped and removed all tracks from the queue.\n" +
                "You have one minute to restore them back to the queue using " + BanterBot4J.BOT_PREFIX + "undostop."
        );
    }

}
