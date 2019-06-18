package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.registry.Registry;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.database.models.DiscordMember;
import com.github.drsmugleaf.reflection.Reflection;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    @Nonnull
    private final Registry COMMAND_REGISTRY;

    public Handler(@Nonnull String commandsPackageName) {
        Reflection reflection = new Reflection(commandsPackageName);
        List<Class<Command>> commands = reflection.findSubtypesOf(Command.class);
        COMMAND_REGISTRY = new Registry(commands);
    }

    @Nonnull
    public Registry getRegistry() {
        return COMMAND_REGISTRY;
    }

    public void handle(@Nonnull MessageCreateEvent event) {
        Optional<Snowflake> guildId = event.getGuildId();
        Optional<User> author = event.getMessage().getAuthor();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);

        commandEvent
                .getMessage()
                .getContent()
                .filter(content -> !content.isEmpty())
                .filter(content -> content.startsWith(BanterBot4J.BOT_PREFIX))
                .filter(content -> author.isPresent())
                .map(content -> Tuples.of(content, author.get().getId()))
                .filter(tuple -> {
                    if (!guildId.isPresent()) {
                        return true;
                    }

                    Long authorId = tuple.getT2().asLong();
                    DiscordMember member = new DiscordMember(authorId, guildId.get().asLong());
                    member.createIfNotExists();
                    member = member.get().get(0);

                    return !member.isBlacklisted;
                })
                .flatMap(tuple -> {
                    CommandSearchResult result = COMMAND_REGISTRY.findCommand(tuple.getT1());
                    if (result == null) {
                        return Optional.empty();
                    }

                    CommandInfo info = result.getEntry().getCommandInfo();
                    if (info == null) {
                        return Optional.of(result);
                    }

                    if (guildId.isPresent()) {
                        List<Permission> commandPermissions = Arrays.asList(info.permissions());

                        return event
                                .getGuild()
                                .flatMap(guild -> guild.getMemberById(tuple.getT2()))
                                .flatMap(Member::getBasePermissions)
                                .zipWith(event.getMessage().getChannel())
                                .flatMap(tuple2 -> {
                                    PermissionSet authorPermissions = tuple2.getT1();
                                    if (!commandPermissions.isEmpty() && !authorPermissions.containsAll(commandPermissions)) {
                                        commandEvent.reply("You don't have permission to use that command.").subscribe();
                                        return Mono.empty();
                                    }

                                    return Mono.just(result);
                                }).blockOptional();
                    } else {
                        return Optional.of(result);
                    }
                })
                .ifPresent(result -> {
                    CommandInfo commandInfo = result.getEntry().getCommandInfo();

                    if (commandInfo != null) {
                        for (Tags tag : commandInfo.tags()) {
                            if (!tag.isValid(event)) {
                                commandEvent.reply(tag.message()).subscribe();
                                return;
                            }
                        }
                    }

                    Command.run(result, commandEvent);
                });
    }

}
