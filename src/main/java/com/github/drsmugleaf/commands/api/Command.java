package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.converter.ConverterRegistry;
import com.github.drsmugleaf.commands.api.converter.Result;
import com.github.drsmugleaf.commands.api.registry.CommandField;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Entry;
import com.github.drsmugleaf.commands.api.tags.Tag;
import com.github.drsmugleaf.commands.api.tags.Tags;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.MessageCreateSpec;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 * Created by DrSmugleaf on 09/06/2018
 */
public abstract class Command implements ICommand {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Command.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    public CommandReceivedEvent EVENT;
    public Arguments ARGUMENTS;
    public User SELF_USER;
    @Nullable
    public Member SELF_MEMBER;

    protected Command() {}

    protected static void run(CommandSearchResult search, CommandReceivedEvent event) {
        Entry entry = search.getEntry();
        Tags[] tags = entry.getCommandInfo().tags();
        for (Tag tag : tags) {
            tag.execute(event);
        }

        Command command = entry.newInstance();
        Arguments arguments = new Arguments(search, event);
        Entry.setEvent(command, event);
        Entry.setArgs(command, arguments);
        Entry.setSelfUser(command, event);
        Entry.setSelfMember(command, event);

        if (command.setArguments(entry)) {
            command.run();
        }
    }

    @Contract("null -> false")
    public static boolean isOwner(@Nullable User user) {
        if (user == null) {
            return false;
        }

        return BanterBot4J.OWNERS.contains(user.getId().asLong());
    }

    public static String getDate() {
        return DATE_FORMAT.format(Instant.now());
    }

    public CommandReceivedEvent getEvent() {
        return EVENT;
    }

    public Arguments getArguments() {
        return ARGUMENTS;
    }

    private boolean setArguments(Entry entry) {
        for (CommandField commandField : entry.getCommandFields()) {
            Field field = commandField.getField();
            field.setAccessible(true);

            Object def;
            try {
                def = field.get(this);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error getting command argument " + field, e);
            }

            Result result = ARGUMENTS.getArg(commandField, def);
            if (!result.isValid()) {
                reply(result.getErrorResponse()).subscribe();
                return false;
            }

            try {
                field.set(this, result.getElement());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error setting command argument " + field, e);
            }
        }

        return true;
    }

    public Mono<Message> reply(Consumer<MessageCreateSpec> spec) {
        return EVENT.reply(spec);
    }

    public Mono<Message> reply(String content) {
        return EVENT.reply(content);
    }

    public void registerConverters(ConverterRegistry converter) {}

}
