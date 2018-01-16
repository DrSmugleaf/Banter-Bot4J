package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.models.Member;
import com.github.drsmugleaf.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
public class Admin {

    @Command(permissions = {Permissions.KICK, Permissions.BAN})
    public static void blacklist(MessageReceivedEvent event, List<String> args) {
        IUser author = event.getAuthor();

        Long guildID = event.getGuild().getLongID();
        List<IUser> mentions = event.getMessage().getMentions();

        IRole highestAuthorRole = Bot.getHighestRole(event.getAuthor(), event.getGuild());

        mentions.forEach(mention -> {
            Member member = Member.get(mention.getLongID(), guildID);
            String nickname = mention.getDisplayName(event.getGuild());

            if(member == null) {
                Bot.sendMessage(event.getChannel(), "User " + nickname + " doesn't exist");
                return;
            }

            if(author.getLongID() == mention.getLongID()) {
                Bot.sendMessage(event.getChannel(), "You can't blacklist yourself!");
                return;
            }

            if(mention.getLongID() == event.getGuild().getOwner().getLongID()) {
                Bot.sendMessage(event.getChannel(), "You can't blacklist the server owner!");
                return;
            }

            IRole highestMentionRole = Bot.getHighestRole(mention, event.getGuild());

            if(highestAuthorRole != null && highestMentionRole != null && highestAuthorRole.getPosition() < highestMentionRole.getPosition()) {
                Bot.sendMessage(event.getChannel(), "You can't blacklist " + nickname + ".\n" +
                        "Your highest role has a lower position in the role manager than their highest role.");
                return;
            }

            member.isBlacklisted = !member.isBlacklisted;
            member.save();
            Bot.sendMessage(event.getChannel(), (member.isBlacklisted ? "Whitelisted user " : "Blacklisted user ") + nickname);
        });
    }

}
