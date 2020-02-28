package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.GuildCommand;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public class GuildCommandEntry<T extends GuildCommand> extends CommandEntry<T> {

    public GuildCommandEntry(Class<T> command) {
        super(command);
    }

    @Override
    public T newInstance(CommandReceivedEvent event, Arguments arguments) {
        T command = super.newInstance(event, arguments);
        if (command == null) {
            return null;
        }

        Optional<Member> member = event
                .getClient()
                .getSelf()
                .zipWith(Mono.justOrEmpty(event.getGuildId()))
                .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2()))
                .blockOptional();

        if (member.isEmpty()) {
            return null;
        }

        command.MEMBER = member.get();
        return command;
    }
}
