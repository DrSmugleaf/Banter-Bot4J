package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.ICommand;

/**
 * Created by DrSmugleaf on 01/09/2018
 */
public class CommandSearchResult<T extends ICommand> {

    private final Entry<T> ENTRY;
    private final String MATCHED_NAME;

    CommandSearchResult(Entry<T> entry, String matchedName) {
        ENTRY = entry;
        MATCHED_NAME = matchedName;
    }

    public Entry<T> getEntry() {
        return ENTRY;
    }

    public String getMatchedName() {
        return MATCHED_NAME;
    }

    public void run(CommandReceivedEvent event) {
        getEntry().executeTags(event);
        Arguments arguments = new Arguments(this, event);
        T command = getEntry().newInstance(event, arguments);

        if (command != null) {
            command.run();
        }
    }

}
