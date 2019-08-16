package com.github.drsmugleaf.commands.api.registry;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult {

    private final Entry ENTRY;
    private final String MATCHED_NAME;

    CommandSearchResult(Entry entry, String matchedName) {
        ENTRY = entry;
        MATCHED_NAME = matchedName;
    }

    public Entry getEntry() {
        return ENTRY;
    }

    public String getMatchedName() {
        return MATCHED_NAME;
    }

}
