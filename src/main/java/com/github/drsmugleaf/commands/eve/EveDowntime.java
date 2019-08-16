package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.model.EveDowntimeUser;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(
        description = "Register yourself to be pinged when the Eve Online server restarts"
)
public class EveDowntime extends Command {

    @Override
    public void run() {
        EVENT
                .getMessage()
                .getAuthor()
                .map(User::getId)
                .map(Snowflake::asLong)
                .map(EveDowntimeUser::new)
                .ifPresent(user -> {
                    if (user.get().isEmpty()) {
                        user.createIfNotExists();
                        reply("I will notify you when the eve online tranquility server comes back up from maintenance each day.").subscribe();
                    } else {
                        user.delete();
                        reply("I will no longer notify you when the server comes back online.").subscribe();
                    }
                });
    }

}
