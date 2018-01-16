package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.models.Member;
import com.github.drsmugleaf.util.Annotations;
import com.github.drsmugleaf.util.Bot;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    @Nonnull
    private static final Map<String, ICommand> COMMANDS = new HashMap<>();

    static {
        List<Method> commands = Annotations.findMethodsWithAnnotations(Command.class);
        for (Method method : commands) {
            ICommand command = (event, args) -> {
                try {
                    method.invoke(method.getClass(), event, args);
                } catch (InvocationTargetException e) {
                    Bot.LOGGER.error("Error running command", e.getCause());
                } catch (IllegalAccessException e) {
                    Bot.LOGGER.error("Error running command", e);
                }
            };

            String commandName = method.getAnnotation(Command.class).name();
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
            COMMANDS.get(commandString).runCommand(event, argsList);
        }
    }

}
