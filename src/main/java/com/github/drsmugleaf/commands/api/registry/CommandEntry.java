package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Command;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public class CommandEntry<T extends Command> extends Entry<T> {

    public CommandEntry(Class<T> command) {
        super(command);
    }

}
