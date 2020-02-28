package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import discord4j.core.object.entity.User;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public class CommandEntry<T extends Command> extends Entry<T> {

    public CommandEntry(Class<T> command) {
        super(command);
    }

    @Override
    public T newInstance(CommandReceivedEvent event, Arguments arguments) {
        T command = super.newInstance(event, arguments);
        if (command == null) {
            return null;
        }

        User user = event
                .getClient()
                .getSelf()
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Unable to get self user"));

        command.EVENT = event;
        command.ARGUMENTS = arguments;
        command.USER = user;

        return command;
    }

}
