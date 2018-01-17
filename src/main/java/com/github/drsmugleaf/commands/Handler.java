package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.models.Member;
import com.github.drsmugleaf.util.Annotations;
import com.github.drsmugleaf.util.Bot;
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
    private static final Map<String, AbstractCommand> COMMANDS = new HashMap<>();

    static {
        List<Method> commands = Annotations.findMethodsWithAnnotations(Command.class);
        for (Method method : commands) {
            Command annotation = method.getAnnotation(Command.class);

            AbstractCommand command = new AbstractCommand() {
                @Override
                void run(@Nonnull MessageReceivedEvent event, @Nonnull List<String> args) {
                    if (event.getGuild() != null) {
                        List<Permissions> annotationPermissions = Arrays.asList(annotation.permissions());
                        EnumSet<Permissions> authorPermissions = event.getAuthor().getPermissionsForGuild(event.getGuild());

                        if (!annotationPermissions.isEmpty() && Collections.disjoint(authorPermissions, Arrays.asList(annotation.permissions()))) {
                            Bot.sendMessage(event.getChannel(), "You don't have permission to use that command.");
                            return;
                        }
                    }

                    for (Tags tags : annotation.tags()) {
                        if (!tags.valid(event)) {
                            Bot.sendMessage(event.getChannel(), tags.message());
                            return;
                        }
                    }

                    try {
                        method.invoke(method.getClass(), event, args);
                    } catch (InvocationTargetException e) {
                        Bot.LOGGER.error("Error running command", e.getCause());
                    } catch (IllegalAccessException e) {
                        Bot.LOGGER.error("Error running command", e);
                    }
                }
            };

            String commandName = annotation.name();
            if (!commandName.isEmpty()) {
                COMMANDS.put(commandName, command);
            } else {
                COMMANDS.put(method.getName(), command);
            }
        }
    }

    @EventSubscriber
    public void handle(@Nonnull MessageReceivedEvent event) {
        String[] argsArray = event.getMessage().getContent().split(" ");

        if (argsArray.length == 0) {
            return;
        }

        if (!argsArray[0].startsWith(Bot.BOT_PREFIX)) {
            return;
        }

        if (event.getGuild() != null) {
            long userID = event.getAuthor().getLongID();
            long guildID = event.getGuild().getLongID();
            Member member = Member.get(userID, guildID);
            if (member != null && member.isBlacklisted) {
                return;
            }
        }

        String commandString = argsArray[0].substring(Bot.BOT_PREFIX.length());
        List<String> argsList = new ArrayList<>(Arrays.asList(argsArray));
        argsList.remove(0);

        if (COMMANDS.containsKey(commandString)) {
            COMMANDS.get(commandString).run(event, argsList);
        }
    }

}
