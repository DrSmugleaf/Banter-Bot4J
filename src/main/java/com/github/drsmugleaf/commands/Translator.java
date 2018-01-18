package com.github.drsmugleaf.commands;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class Translator {

    @Command
    public static void bridge(@Nonnull MessageReceivedEvent event, List<String> args) {}

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {}

}
