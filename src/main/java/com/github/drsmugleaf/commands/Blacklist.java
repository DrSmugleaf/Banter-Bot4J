package com.github.drsmugleaf.commands;

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

    @Nonnull
    private static String wrongFormatResponse() {
        return "**Formats:**\n" +
               BOT_PREFIX + "blacklist @User1 @User2 @User3\n" +
               "**Examples:**\n" +
               BOT_PREFIX + "blacklist @DrSmugleaf @Banter Bot4J";
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        if (event.ARGS.isEmpty()) {
            event.reply(wrongFormatResponse());
            return;
        }

        IUser author = event.getAuthor();

        Long guildID = event.getGuild().getLongID();
        List<IUser> mentions = event.getMessage().getMentions();

        IRole highestAuthorRole = event.getHighestAuthorRole();

        mentions.forEach(mention -> {
            Member member = new Member(mention.getLongID(), guildID).get().get(0);
            String nickname = mention.getDisplayName(event.getGuild());

            if(member == null) {
                event.reply("User " + nickname + " doesn't exist");
                return;
            }

            if(author.getLongID() == mention.getLongID()) {
                event.reply("You can't blacklist yourself!");
                return;
            }

            if(mention.getLongID() == event.getGuild().getOwner().getLongID()) {
                event.reply("You can't blacklist the server owner!");
                return;
            }

            IRole highestMentionRole = CommandReceivedEvent.getHighestRole(mention, event.getGuild());

            if(highestAuthorRole != null && highestMentionRole != null && highestAuthorRole.getPosition() < highestMentionRole.getPosition()) {
                event.reply("You can't blacklist " + nickname + ".\n" +
                            "Your highest role has a lower position in the role manager than their highest role.");
                return;
            }

            member.isBlacklisted = !member.isBlacklisted;
            member.save();
            event.reply((member.isBlacklisted ? "Whitelisted user " : "Blacklisted user ") + nickname);
        });
    }

}
