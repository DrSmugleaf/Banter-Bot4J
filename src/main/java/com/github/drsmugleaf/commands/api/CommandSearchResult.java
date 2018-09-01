package com.github.drsmugleaf.commands.api;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult {

    @Nonnull
    public final Class<Command> COMMAND;

    @Nonnull
    public final String MATCHED_NAME;

    CommandSearchResult(@Nonnull Class<Command> command, @Nonnull String matchedName) {
        COMMAND = command;
        MATCHED_NAME = matchedName;
    }

}
