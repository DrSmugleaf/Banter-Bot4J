package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RoleBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 23/01/2018.
 */
public class Color extends AbstractCommand {

    @Nullable
    private static java.awt.Color resolve(String string) {
        java.awt.Color color = null;
        try {
            color = java.awt.Color.decode(string);
        } catch (NumberFormatException nfe) {
            try {
                color = java.awt.Color.decode("#" + string);
            } catch (NumberFormatException nfe2) {
                try {
                    color = (java.awt.Color) java.awt.Color.class.getField(string.trim().toUpperCase().replace(" ", "_")).get(null);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    BanterBot4J.LOGGER.warn("Error resolving color " + string, e);
                }
            }
        }

        return color;
    }

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
            try {
                author.removeRole(role);
            } catch (MissingPermissionsException e) {
                String missingPermissions = e.getMissingPermissions().stream().map(Permissions::name).collect(Collectors.joining(", "));
                sendMessage(channel, "I don't have permission to change your name color.\n" +
                                     "Missing permissions: " + missingPermissions);
                return;
            }
            usersByRole.remove(author);
            if (usersByRole.isEmpty()) {
                role.delete();
            }
            String hexCode = String.format("#%06x", role.getColor().getRGB() & 0x00FFFFFF).toUpperCase();
            sendMessage(channel, "Removed your name color. It was " + hexCode);
            return;
        }

        String requestedColor = args.get(0);
        java.awt.Color color = resolve(requestedColor);
        if (color == null) {
            sendMessage(channel, "Invalid color. Make sure it is a hexadecimal string (0000FF) or a simple color like red.");
            return;
        }

        if (roles.isEmpty()) {
            try {
                IRole newRole = new RoleBuilder(guild)
                        .withName("color-" + author.getStringID())
                        .withColor(color)
                        .build();
                author.addRole(newRole);
            } catch (MissingPermissionsException e) {
                String missingPermissions = e.getMissingPermissions().stream().map(Permissions::name).collect(Collectors.joining(", "));
                sendMessage(channel, "I don't have permission to change your name color.\n" +
                                     "Missing permissions: " + missingPermissions);
                return;
            }
        } else {
            IRole role = roles.get(0);
            try {
                role.changeColor(color);
            } catch (MissingPermissionsException e) {
                String missingPermissions = e.getMissingPermissions().stream().map(Permissions::name).collect(Collectors.joining(", "));
                sendMessage(channel, "I don't have permission to change your name color.\n" +
                                     "Missing permissions: " + missingPermissions);
                return;
            }
        }
        sendMessage(channel, "Changed your name color to " + requestedColor);
    }

}
