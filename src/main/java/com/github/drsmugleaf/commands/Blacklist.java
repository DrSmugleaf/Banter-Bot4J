package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.Member;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
@CommandInfo(permissions = {Permissions.KICK, Permissions.BAN})
public class Blacklist extends Command {

    protected Blacklist(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Nonnull
    private static String invalidArgumentsResponse() {
        return "Invalid arguments.\n" +
               "**Formats:**\n" +
               BanterBot4J.BOT_PREFIX + "blacklist @User1 @User2 @User3\n" +
               "**Examples:**\n" +
               BanterBot4J.BOT_PREFIX + "blacklist @DrSmugleaf @Banter Bot4J";
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            EVENT.reply(invalidArgumentsResponse());
            return;
        }

        IUser author = EVENT.getAuthor();

        Long guildID = EVENT.getGuild().getLongID();
        List<IUser> mentions = EVENT.getMessage().getMentions();

        IRole highestAuthorRole = EVENT.getHighestAuthorRole();

        mentions.forEach(mention -> {
            Member member = new Member(mention.getLongID(), guildID).get().get(0);
            String nickname = mention.getDisplayName(EVENT.getGuild());

            if (member == null) {
                EVENT.reply("User " + nickname + " doesn't exist");
                return;
            }

            if (author.getLongID() == mention.getLongID()) {
                EVENT.reply("You can't blacklist yourself!");
                return;
            }

            if (mention.getLongID() == EVENT.getGuild().getOwner().getLongID()) {
                EVENT.reply("You can't blacklist the server owner!");
                return;
            }

            IRole highestMentionRole = CommandReceivedEvent.getHighestRole(mention, EVENT.getGuild());

            if (highestAuthorRole != null && highestMentionRole != null && highestAuthorRole.getPosition() < highestMentionRole.getPosition()) {
                EVENT.reply("You can't blacklist " + nickname + ".\n" +
                            "Your highest role has a lower position in the role manager than their highest role.");
                return;
            }

            member.isBlacklisted = !member.isBlacklisted;
            member.save();
            EVENT.reply((member.isBlacklisted ? "Whitelisted user " : "Blacklisted user ") + nickname);
        });
    }

}
