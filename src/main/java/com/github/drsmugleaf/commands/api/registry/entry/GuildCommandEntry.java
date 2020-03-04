package com.github.drsmugleaf.commands.api.registry.entry;

import com.github.drsmugleaf.commands.api.arguments.Arguments;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.GuildCommand;
import com.github.drsmugleaf.commands.api.converter.result.Result;
import reactor.core.publisher.Mono;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public class GuildCommandEntry<T extends GuildCommand> extends CommandEntry<T> {

    public GuildCommandEntry(Class<T> command) {
        super(command);
    }

    @Override
    public Result<T> newInstance(CommandReceivedEvent event, Arguments arguments) {
        if (event.getGuildId().isEmpty()) {
            return new Result<>(null, "That command must be used in a server channel.");
        }

        Result<T> result = super.newInstance(event, arguments);
        T command = result.getElement();
        if (command == null) {
            return result;
        }

        command.SELF_MEMBER = event
                .getClient()
                .getSelf()
                .zipWith(Mono.justOrEmpty(event.getGuildId()))
                .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2()))
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Unable to get self selfMember"));

        return result;
    }
}
