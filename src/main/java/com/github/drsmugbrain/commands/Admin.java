package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.models.Member;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
public class Admin {

    public static void blacklist(MessageReceivedEvent event, List<String> args) {
        EnumSet<Permissions> permissions = event.getAuthor().getPermissionsForGuild(event.getGuild());

        if(permissions.contains(Permissions.KICK) || permissions.contains(Permissions.BAN)) {
            Long guildID = event.getGuild().getLongID();
            List<IUser> mentions = event.getMessage().getMentions();

            mentions.forEach(mention -> {
                Member member = Member.get(mention.getLongID(), guildID);
                String nickname = mention.getDisplayName(event.getGuild());

                if(member == null) {
                    RequestBuffer.request(() -> event.getChannel().sendMessage("User " + nickname + " doesn't exist"));
                    return;
                }

                event.getChannel().sendMessage((member.isBlacklisted ? "Whitelisted user " : "Blacklisted user ") + nickname);
                member.isBlacklisted = !member.isBlacklisted;
                member.save();
            });
        }
    }

}
