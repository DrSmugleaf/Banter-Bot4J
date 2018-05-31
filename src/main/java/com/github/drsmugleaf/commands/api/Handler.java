package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.database.models.Member;
import com.github.drsmugleaf.util.Reflection;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    @Nonnull
    private static final Map<String, CommandRunnable> COMMANDS = new HashMap<>();

    static {
        Reflection reflection = new Reflection("com.github.drsmugleaf.commands");
        List<Method> commands = reflection.findMethodsWithAnnotation(CommandInfo.class);
        for (Method method : commands) {
            CommandInfo annotation = method.getAnnotation(CommandInfo.class);

            CommandRunnable command = (event) -> {
                if (event.getGuild() != null) {
                    List<Permissions> annotationPermissions = Arrays.asList(annotation.permissions());
                    EnumSet<Permissions> authorPermissions = event.getAuthor().getPermissionsForGuild(event.getGuild());

                    if (!annotationPermissions.isEmpty() && Collections.disjoint(authorPermissions, Arrays.asList(annotation.permissions()))) {
                        CommandReceivedEvent.sendMessage(event.getChannel(), "You don't have permission to use that command.");
                        return;
                    }
                }

                for (Tags tags : annotation.tags()) {
                    if (!tags.valid(event)) {
                        CommandReceivedEvent.sendMessage(event.getChannel(), tags.message());
                        return;
                    }

                    tags.execute(event);
                }

                try {
                    method.invoke(method.getClass(), event);
                } catch (InvocationTargetException e) {
                    BanterBot4J.LOGGER.error("Error running command", e.getCause());
                } catch (IllegalAccessException e) {
                    BanterBot4J.LOGGER.error("Error running command", e);
                }
            };

            String commandName = annotation.name();
            if (!commandName.isEmpty()) {
                COMMANDS.put(commandName.toLowerCase(), command);
            } else {
                COMMANDS.put(method.getName().toLowerCase(), command);
            }
        }
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        String[] argsArray = event.getMessage().getContent().split(" ");

        if (argsArray.length == 0) {
            return;
        }

        if (!argsArray[0].startsWith(BanterBot4J.BOT_PREFIX)) {
            return;
        }

        if (event.getGuild() != null) {
            long userID = event.getAuthor().getLongID();
            long guildID = event.getGuild().getLongID();
            Member member = new Member(userID, guildID).get().get(0);
            if (member != null && member.isBlacklisted) {
                return;
            }
        }

        String commandString = argsArray[0].substring(BanterBot4J.BOT_PREFIX.length()).toLowerCase();

        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);

        if (COMMANDS.containsKey(commandString)) {
            COMMANDS.get(commandString).run(commandEvent);
        }
    }

}
