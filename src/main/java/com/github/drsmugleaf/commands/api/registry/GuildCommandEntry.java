package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.GuildCommand;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public class GuildCommandEntry extends Entry<GuildCommand> {

    public GuildCommandEntry(Class<GuildCommand> command) {
        super(command);
    }

    @Override
    public GuildCommand newInstance(CommandReceivedEvent event, Arguments arguments) {
        GuildCommand command = super.newInstance(event, arguments);
        Member member = event
                .getClient()
                .getSelf()
                .zipWith(Mono.justOrEmpty(event.getGuildId()))
                .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2()))
                .blockOptional()
                .orElse(null);

        try {
            GuildCommand.class.getDeclaredField("MEMBER").set(command, member);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalStateException("Error setting member field for guild command " + command, e);
        }

        return command;
    }
}
