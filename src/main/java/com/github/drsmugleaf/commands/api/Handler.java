package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.database.models.Member;
import com.github.drsmugleaf.reflection.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    @Nonnull
    static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    @Nonnull
    private static final Map<String, Class<Command>> COMMANDS = getCommands();

    private static Map<String, Class<Command>> getCommands() {
        Map<String, Class<Command>> commands = new HashMap<>();
        Reflection reflection = new Reflection("com.github.drsmugleaf.commands");

        for (Class<Command> command : reflection.findSubtypesOf(Command.class)) {
            CommandInfo annotation = command.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null && !annotation.name().isEmpty()) {
                commands.put(annotation.name().toLowerCase(), command);
            } else {
                commands.put(command.getSimpleName().toLowerCase(), command);
            }
        }

        return commands;
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
            Member member = new Member(userID, guildID);
            member.createIfNotExists();
            member = member.get().get(0);
            if (member.isBlacklisted) {
                return;
            }
        }

        String commandString = argsArray[0].substring(BanterBot4J.BOT_PREFIX.length()).toLowerCase();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);
        if (COMMANDS.containsKey(commandString)) {
            Class<Command> commandClass = COMMANDS.get(commandString);
            Command.run(commandClass, commandEvent);
        }
    }

}
