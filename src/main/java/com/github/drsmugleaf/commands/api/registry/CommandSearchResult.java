package com.github.drsmugleaf.commands.api.registry;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult {

    @Nonnull
    private final Entry ENTRY;

    @Nonnull
    private final String MATCHED_NAME;

    CommandSearchResult(@Nonnull Entry entry, @Nonnull String matchedName) {
        ENTRY = entry;
        MATCHED_NAME = matchedName;
    }

    @Nonnull
    public Entry getEntry() {
        return ENTRY;
    }

    @Nonnull
    public String getMatchedName() {
        return MATCHED_NAME;
    }

}
