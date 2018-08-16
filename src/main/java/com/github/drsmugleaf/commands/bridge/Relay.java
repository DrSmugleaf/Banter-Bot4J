package com.github.drsmugleaf.commands.bridge;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 21/07/2018
 */
@CommandInfo(aliases = {"rylai", "rylai's crystal scepter"}, permissions = {Permissions.MANAGE_CHANNELS})
public class Relay extends Command {

    protected Relay(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {

    }

}
