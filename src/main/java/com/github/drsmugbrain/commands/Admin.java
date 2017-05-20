package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.models.Member;
import com.github.drsmugbrain.util.Bot;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
public class Admin {

    public static void blacklist(MessageReceivedEvent event, List<String> args) {
        IUser author = event.getAuthor();
        EnumSet<Permissions> permissions = author.getPermissionsForGuild(event.getGuild());

        if(permissions.contains(Permissions.KICK) || permissions.contains(Permissions.BAN)) {
            Long guildID = event.getGuild().getLongID();
            List<IUser> mentions = event.getMessage().getMentions();

            List<IRole> authorRoles = author.getRolesForGuild(event.getGuild());
            authorRoles.sort(Comparator.comparingInt(IRole::getPosition));
            IRole highestAuthorRole = authorRoles.get(authorRoles.size() - 1);

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

                List<IRole> mentionRoles = mention.getRolesForGuild(event.getGuild());
                mentionRoles.sort(Comparator.comparingInt(IRole::getPosition));
                IRole highestMentionRole = mentionRoles.get(mentionRoles.size() - 1);

                if(highestAuthorRole.getPosition() < highestMentionRole.getPosition()) {
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

}
