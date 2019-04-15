package com.github.drsmugleaf.commands.api.tags;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MissingPermissionsException;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 16/01/2018.
 */
public enum Tags implements Tag {

    DELETE_COMMAND_MESSAGE {

        @Override
        public void execute(@Nonnull CommandReceivedEvent event) {
            try {
                event.getMessage().delete();
            } catch (MissingPermissionsException ignored) {}
        }

    },
    GUILD_ONLY {

        @Override
        public boolean isValid(@Nonnull CommandReceivedEvent event) {
            return event.getGuild() != null;
        }

        @Nonnull
        @Override
        public String message() {
            return "That command must be used in a server channel.";
        }

    },
    OWNER_ONLY {

        @Override
        public boolean isValid(@Nonnull CommandReceivedEvent event) {
            return Command.isOwner(event.getAuthor());
        }

        @Nonnull
        @Override
        public String message() {
            return "You don't have permission to use that command.";
        }

    },
    SAME_VOICE_CHANNEL {

        @Override
        public boolean isValid(@Nonnull CommandReceivedEvent event) {
            if (!GUILD_ONLY.isValid(event)) {
                return false;
            }

            IGuild guild = event.getGuild();
            IUser author = event.getAuthor();
            IVoiceChannel authorVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();
            IUser bot = event.getClient().getOurUser();
            IVoiceChannel botVoiceChannel = bot.getVoiceStateForGuild(guild).getChannel();

            return botVoiceChannel == authorVoiceChannel;
        }

        @Nonnull
        @Override
        public String message() {
            return "You aren't in the same voice channel as me.";
        }

    },
    VOICE_ONLY {

        @Override
        public boolean isValid(@Nonnull CommandReceivedEvent event) {
            if (!GUILD_ONLY.isValid(event)) {
                return false;
            }

            IGuild guild = event.getGuild();
            IUser author = event.getAuthor();
            IVoiceChannel authorVoiceChannel = author.getVoiceStateForGuild(guild).getChannel();

            return authorVoiceChannel != null;
        }

        @Nonnull
        @Override
        public String message() {
            return "You must be in a voice channel to use that command.";
        }

    };

    public boolean isValid(@Nonnull CommandReceivedEvent event) {
        return true;
    }

    @Nonnull
    public String message() {
        return "You can't use that command.";
    }

    public void execute(@Nonnull CommandReceivedEvent event) {}

}
