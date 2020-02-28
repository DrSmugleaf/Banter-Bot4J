package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.registry.CommandRegistry;
import com.github.drsmugleaf.commands.api.registry.CommandSearchResult;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.database.model.DiscordMember;
import com.github.drsmugleaf.reflection.Reflection;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Handler {

    private final CommandRegistry COMMAND_REGISTRY;

    public Handler(String commandsPackageName) {
        Reflection reflection = new Reflection(commandsPackageName);
        List<Class<Command>> commands = reflection.findSubtypesOf(Command.class);
        COMMAND_REGISTRY = new CommandRegistry(commands);
    }

    public CommandRegistry getRegistry() {
        return COMMAND_REGISTRY;
    }

    public Mono<Void> handle(MessageCreateEvent event) {
        Optional<Snowflake> guildId = event.getGuildId();
        Optional<User> author = event.getMessage().getAuthor();
        CommandReceivedEvent commandEvent = new CommandReceivedEvent(event);

        return Mono
                .just(commandEvent)
                .map(CommandReceivedEvent::getMessage)
                .flatMap(m -> Mono.justOrEmpty(m.getContent()))
                .filter(content -> !content.isEmpty())
                .filter(content -> content.startsWith(BanterBot4J.BOT_PREFIX))
                .filter(content -> author.isPresent())
                .filter(content -> !author.map(User::isBot).get())
                .map(content -> Tuples.of(content, author.get().getId()))
                .filter(tuple -> {
                    if (guildId.isEmpty()) {
                        return true;
                    }

                    Long authorId = tuple.getT2().asLong();
                    DiscordMember member = new DiscordMember(authorId, guildId.get().asLong(), null);
                    member.createIfNotExists();
                    member = member.get().get(0);

                    return !member.isBlacklisted;
                })
                .flatMap(tuple -> {
                    CommandSearchResult<?> search = COMMAND_REGISTRY.findCommand(tuple.getT1());
                    if (search == null) {
                        return Mono.empty();
                    }

                    if (guildId.isEmpty()) {
                        return Mono.just(search);
                    }

                    return event
                            .getGuild()
                            .flatMap(guild -> guild.getMemberById(tuple.getT2()))
                            .flatMap(member -> member.getBasePermissions())
                            .zipWith(event.getMessage().getChannel())
                            .flatMap(tuple2 -> {
                                Permission[] permissions = search.getEntry().getCommandInfo().permissions();
                                List<Permission> commandPermissions = Arrays.asList(permissions);
                                PermissionSet authorPermissions = tuple2.getT1();

                                if (!commandPermissions.isEmpty() && !authorPermissions.containsAll(commandPermissions)) {
                                    commandEvent.reply("You don't have permission to use that command.").subscribe();
                                    return Mono.empty();
                                }

                                return Mono.just(search);
                            });
                })
                .doOnNext(result -> {
                    Tags[] tags = result.getEntry().getCommandInfo().tags();

                    for (Tags tag : tags) {
                        if (!tag.isValid(event)) {
                            commandEvent.reply(tag.message()).subscribe();
                            return;
                        }
                    }

                    result.run(commandEvent);
                })
                .then();
    }

}
