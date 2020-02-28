package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.converter.TransformerSet;
import com.github.drsmugleaf.commands.api.registry.CommandEntry;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.MessageCreateSpec;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

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
    public User USER;

    protected Command() {}

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

    @Override
    public TransformerSet getTransformers() {
        return TransformerSet.of();
    }

    @Override
    public CommandEntry<? extends Command> toEntry() {
        return new CommandEntry<>(getClass());
    }

    public Mono<Message> reply(Consumer<MessageCreateSpec> spec) {
        return EVENT.reply(spec);
    }

    public Mono<Message> reply(String content) {
        return EVENT.reply(content);
    }

}
