package com.github.drsmugleaf.commands.bridge;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 21/07/2018
 */
@CommandInfo(aliases = {"rylai", "rylai's crystal scepter"}, permissions = {Permissions.MANAGE_CHANNELS})
public class Relay extends Command {

    @Nonnull
    static final Logger LOGGER = LoggerFactory.getLogger(Relay.class);

    protected Relay(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        if (ARGS.isEmpty()) {
            event.reply("You didn't provide a relay type.\n" +
                        "Usage: " + botPrefix + "relay type\n" +
                        "Where type is one of: " + Types.getAllNames());
            return;
        }

        String typeName = ARGS.first();
        Types type = Types.from(typeName);
        if (type == null) {
            event.reply("Invalid relay type: " + typeName + "\n" +
                        "Available types: " + Types.getAllNames());
            return;
        }

        Validation validation = type.setupRelay(ARGS, event);
        if (validation.RESPONSE != null) {
            event.reply(validation.RESPONSE);
        }

        if (validation.IS_VALID) {
            event.reply("Established relay");
        }
    }

}
