package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.EveDowntimeUser;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
public class EveDowntime extends Command {

    protected EveDowntime(@NotNull CommandReceivedEvent event, @NotNull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        long authorID = EVENT.getAuthor().getLongID();
        EveDowntimeUser user = new EveDowntimeUser(authorID);
        if (user.get().isEmpty()) {
            user.createIfNotExists();
            EVENT.reply("I will notify you when the eve online tranquility server comes back up from maintenance each day.");
        } else {
            user.delete();
            EVENT.reply("I will no longer notify you when the server comes back online.");
        }
    }

}
