package com.github.drsmugleaf.commands;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
interface CommandRunnable {

    void run(@Nonnull MessageReceivedEvent event, @Nonnull List<String> args);

}
