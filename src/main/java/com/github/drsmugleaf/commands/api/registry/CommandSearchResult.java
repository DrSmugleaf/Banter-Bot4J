package com.github.drsmugleaf.commands.api.registry;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult {

    @Nonnull
    public final Entry COMMAND;

    @Nonnull
    public final String MATCHED_NAME;

    CommandSearchResult(@Nonnull Entry command, @Nonnull String matchedName) {
        COMMAND = command;
        MATCHED_NAME = matchedName;
    }

}
