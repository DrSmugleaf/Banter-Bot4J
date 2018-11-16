package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Command;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult {

    @NotNull
    public final Class<? extends Command> COMMAND;

    @NotNull
    public final String MATCHED_NAME;

    CommandSearchResult(@NotNull Class<? extends Command> command, @NotNull String matchedName) {
        COMMAND = command;
        MATCHED_NAME = matchedName;
    }

}
