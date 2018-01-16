package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 16/01/2018.
 */
enum Tags {

    GUILD_ONLY {
        @Override
        public boolean valid(@Nonnull MessageReceivedEvent event) {
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
        public boolean valid(@Nonnull MessageReceivedEvent event) {
            return Bot.isOwner(event.getAuthor().getLongID());
        }

        @Nonnull
        @Override
        public String message() {
            return "You don't have permission to use that command.";
        }
    },
    SAME_VOICE_CHANNEL {
        @Override
        public boolean valid(@Nonnull MessageReceivedEvent event) {
            if (!GUILD_ONLY.valid(event)) {
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
        public boolean valid(@Nonnull MessageReceivedEvent event) {
            if (!GUILD_ONLY.valid(event)) {
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

    public abstract boolean valid(@Nonnull MessageReceivedEvent event);

    @Nonnull
    public abstract String message();

}
