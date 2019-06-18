package com.github.drsmugleaf.commands.api.tags;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public interface Tag {

    boolean isValid(@Nonnull MessageCreateEvent event);

    String message();

    void execute(@Nonnull CommandReceivedEvent event);

}
