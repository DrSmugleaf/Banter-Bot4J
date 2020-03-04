package com.github.drsmugleaf.commands.api.registry;

import com.github.drsmugleaf.commands.api.arguments.Arguments;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.ICommand;
import com.github.drsmugleaf.commands.api.converter.result.Result;
import com.github.drsmugleaf.commands.api.registry.entry.Entry;

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
        Result<T> result = getEntry().newInstance(event, arguments);

        if (result.getErrorResponse() != null) {
            event.reply(result.getErrorResponse()).subscribe();
        } else {
            result.getElement().run();
        }
    }

}
