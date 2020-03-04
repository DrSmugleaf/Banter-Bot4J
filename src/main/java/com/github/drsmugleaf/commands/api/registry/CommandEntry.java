package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.converter.Result;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public class CommandEntry<T extends Command> extends Entry<T> {

    public CommandEntry(Class<T> command) {
        super(command);
    }

    @Override
    public Result<T> newInstance(CommandReceivedEvent event, Arguments arguments) {
        Result<T> result = super.newInstance(event, arguments);
        T command = result.getElement();
        if (command == null) {
            return result;
        }

        command.EVENT = event;
        command.ARGUMENTS = arguments;
        command.USER = event
                .getClient()
                .getSelf()
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Unable to get self selfUser"));

        return result;
    }

}
