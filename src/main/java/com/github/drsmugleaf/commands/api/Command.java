package com.github.drsmugleaf.commands.api;

import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public abstract class Command {

    protected static <T extends Command> void run(@Nonnull Class<T> commandClass, @Nonnull CommandReceivedEvent event) {
        CommandInfo annotation = commandClass.getDeclaredAnnotation(CommandInfo.class);

        if (annotation != null) {
            if (event.getGuild() != null) {
                List<Permissions> annotationPermissions = Arrays.asList(annotation.permissions());
                EnumSet<Permissions> authorPermissions = event.getAuthor().getPermissionsForGuild(event.getGuild());

                if (!annotationPermissions.isEmpty() && Collections.disjoint(authorPermissions, Arrays.asList(annotation.permissions()))) {
                    event.reply("You don't have permission to use that command.");
                    return;
                }
            }

            for (Tag tags : annotation.tags()) {
                if (!tags.isValid(event)) {
                    event.reply(tags.message());
                    return;
                }

                tags.execute(event);
            }
        }

        Command command;
        try {
            command = commandClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Handler.LOGGER.error("Error running command " + commandClass.getName(), e);
            return;
        }

        command.run(event);
    }

    protected abstract void run(@Nonnull CommandReceivedEvent event);

}
