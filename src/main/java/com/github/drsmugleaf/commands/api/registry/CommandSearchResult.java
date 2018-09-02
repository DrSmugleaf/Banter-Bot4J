package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Command;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult {

    @Nonnull
    public final Class<? extends Command> COMMAND;

    @Nonnull
    public final String MATCHED_NAME;

    CommandSearchResult(@Nonnull Class<? extends Command> command, @Nonnull String matchedName) {
        COMMAND = command;
        MATCHED_NAME = matchedName;
    }

}
