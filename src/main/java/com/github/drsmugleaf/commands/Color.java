package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RoleBuilder;

import java.util.List;

/**
 * Created by DrSmugleaf on 23/01/2018.
 */
public class Color extends AbstractCommand {

    @Command(tags = {Tags.GUILD_ONLY})
    public static void color(MessageReceivedEvent event, List<String> args) {
        IGuild guild = event.getGuild();
        IChannel channel = event.getChannel();
        IUser author = event.getAuthor();

        List<IRole> roles = guild.getRolesByName("color-" + author.getStringID());
        if (args.isEmpty()) {
            if (roles.isEmpty()) {
                sendMessage(channel, "You don't have a name color. Use " + BanterBot4J.BOT_PREFIX + "color name OR hexadecimal code to assign one.");
                return;
            }

            IRole role = roles.get(0);
            List<IUser> usersByRole = guild.getUsersByRole(role);
            author.removeRole(role);
            usersByRole.remove(author);
            if (usersByRole.isEmpty()) {
                role.delete();
            }
            String hexCode = String.format("#%06x", role.getColor().getRGB() & 0x00FFFFFF).toUpperCase();
            sendMessage(channel, "Removed your name color. It was " + hexCode);
            return;
        }

        String requestedColor = args.get(0);
        java.awt.Color color;
        try {
            color = java.awt.Color.decode(requestedColor);
        } catch (NumberFormatException nfe) {
            try {
                color = (java.awt.Color) java.awt.Color.class.getField(requestedColor.trim().toUpperCase().replace(" ", "_")).get(null);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                BanterBot4J.LOGGER.warn("Error decoding color " + requestedColor, e);
                sendMessage(channel, "Invalid color. Make sure it is a hexadecimal string (#0000FF) or a simple color like red.");
                return;
            }
        }

        if (roles.isEmpty()) {
            IRole newRole = new RoleBuilder(guild)
                    .withName("color-" + author.getStringID())
                    .withColor(color)
                    .build();
            author.addRole(newRole);
        } else {
            IRole role = roles.get(0);
            role.changeColor(color);
        }
        sendMessage(channel, "Changed your name color to " + requestedColor);
        return;
    }

}
