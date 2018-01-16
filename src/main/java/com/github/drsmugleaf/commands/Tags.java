package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 16/01/2018.
 */
enum Tags {

    OWNER_ONLY {
        @Override
        public boolean valid(@Nonnull MessageReceivedEvent event) {
            return Bot.isOwner(event.getAuthor().getLongID());
        }

        @Override
        public String message() {
            return "You don't have permission to use that command.";
        }
    };

    public abstract boolean valid(@Nonnull MessageReceivedEvent event);

    public abstract String message();

}
