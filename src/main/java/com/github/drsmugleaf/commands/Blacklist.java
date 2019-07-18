package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.models.DiscordMember;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
@CommandInfo(
        permissions = {Permission.KICK_MEMBERS},
        description = "Blacklist an user from using the bot"
)
public class Blacklist extends Command {

    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "blacklist @User1 @User2 @User3\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "blacklist @DrSmugleaf @Banter Bot4J";
    }

    @Override
    public void run() {
        if (ARGUMENTS.isEmpty()) {
            reply(invalidArgumentsResponse()).subscribe();
            return;
        }

        Flux<Member> mentions = EVENT
                .getMessage()
                .getUserMentions()
                .zipWith(EVENT.getGuild().repeat())
                .flatMap(tuple -> tuple.getT1().asMember(tuple.getT2().getId()))
                .zipWith(Mono.justOrEmpty(EVENT.getMember()).repeat())
                .handle((tuple, sink) -> {
                    Member mention = tuple.getT1();
                    Member author = tuple.getT2();
                    if (mention.equals(author)) {
                        reply("You can't blacklist yourself!").subscribe();
                        return;
                    }

                    sink.next(mention);
                }).cast(Member.class)
                .zipWith(EVENT.getGuild().flatMap(Guild::getOwner).repeat())
                .handle((tuple, sink) -> {
                    Member mention = tuple.getT1();
                    Member guildOwner = tuple.getT2();
                    if (mention.equals(guildOwner)) {
                        reply("You can't blacklist the server owner!").subscribe();
                        return;
                    }

                    sink.next(mention);
                });

        mentions
                .zipWith(mentions.flatMapSequential(Member::getHighestRole))
                .zipWith(EVENT.getMember().map(Member::getHighestRole).orElse(Mono.empty()).repeat())
                .handle((tuple, sink) -> {
                    Tuple2<Member, Role> mention = tuple.getT1();
                    Member mentionMember = mention.getT1();
                    Role mentionRole = mention.getT2();

                    Role authorRole = tuple.getT2();

                    if (authorRole.getRawPosition() < mentionRole.getRawPosition()) {
                        reply(
                                "You can't blacklist " + mentionMember.getDisplayName() + ".\n" +
                                "Your highest role has a lower position in the role manager than their highest role."
                        ).subscribe();

                        return;
                    }

                    sink.next(mentionMember);
                }).cast(Member.class)
                .map(member -> Tuples.of(member, new DiscordMember(member.getId().asLong(), member.getGuildId().asLong(), null)))
                .subscribe(tuple -> {
                    Member mention = tuple.getT1();
                    DiscordMember member = tuple.getT2();

                    member.isBlacklisted = !member.isBlacklisted;
                    member.save();

                    reply((member.isBlacklisted ? "Blacklisted " : "Whitelisted ") + mention.getDisplayName()).subscribe();
                });
    }

}
