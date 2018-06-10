package com.github.drsmugleaf.commands.api;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public interface Tag {

    boolean isValid(@Nonnull MessageReceivedEvent event);

    String message();

    void execute(@Nonnull MessageReceivedEvent event);

}
