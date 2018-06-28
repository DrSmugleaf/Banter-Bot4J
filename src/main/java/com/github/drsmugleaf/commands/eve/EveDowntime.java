package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.EveDowntimeUser;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
public class EveDowntime extends Command {

    protected EveDowntime(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        long authorID = event.getAuthor().getLongID();
        EveDowntimeUser user = new EveDowntimeUser(authorID);
        if (user.get().isEmpty()) {
            user.createIfNotExists();
            event.reply("I will notify you when the eve online tranquility server comes back up from maintenance each day.");
        } else {
            user.delete();
            event.reply("I will no longer notify you when the server comes back online.");
        }
    }

}
