package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.GuildCommand;
import com.github.drsmugleaf.commands.api.converter.transformer.TransformerSet;
import com.github.drsmugleaf.database.model.DiscordMember;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
@CommandInfo(
        permissions = {Permission.KICK_MEMBERS},
        description = "Blacklist an user from using the bot"
)
public class Blacklist extends GuildCommand {

    @Argument(position = 1, examples = "@DrSmugleaf @Banter Bot4J", maxWords = Integer.MAX_VALUE)
    private Member[] mentions;

    @Override
    public void run() {
        for (Member mention : mentions) {
            DiscordMember member = new DiscordMember(mention.getId().asLong(), mention.getGuildId().asLong(), null).get().get(0);

            member.isBlacklisted = !member.isBlacklisted;
            member.save();

            reply((member.isBlacklisted ? "Blacklisted " : "Whitelisted ") + mention.getDisplayName()).subscribe();
        }
    }

    @Override
    public TransformerSet getTransformers() {
        return TransformerSet.of(
                Member[].class,
                (s, e) -> e
                        .getMessage()
                        .getUserMentions()
                        .zipWith(e.getGuild().repeat())
                        .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2().getId()))
                        .toStream()
                        .toArray(Member[]::new),
                context -> {
                    Member author = context.getEvent().getMessage().getAuthorAsMember().blockOptional().orElse(null);
                    if (author == null) {
                        throw new IllegalArgumentException("No author found");
                    }

                    Integer authorHighestRole = author.getHighestRole().flatMap(Role::getPosition).blockOptional().orElse(null);
                    if (authorHighestRole == null) {
                        throw new IllegalArgumentException("No highest role found for author");
                    }

                    Member guildOwner = context.getEvent().getGuild().flatMap(Guild::getOwner).blockOptional().orElse(null);
                    for (Member mention : context.getValue()) {
                        if (mention.equals(author)) {
                            return "You can't blacklist yourself!";
                        }

                        if (mention.equals(guildOwner)) {
                            return "You can't blacklist the server owner!";
                        }

                        Integer mentionHighestRole = mention.getHighestRole().flatMap(Role::getPosition).blockOptional().orElse(null);
                        if (mentionHighestRole != null && authorHighestRole < mentionHighestRole) {
                            return "You can't blacklist " + mention.getDisplayName() + ".\n" +
                                    "Your highest role has a lower position in the role manager than their highest role.";
                        }
                    }

                    return null;
                }
        );
    }

}
