package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RoleBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 23/01/2018.
 */
@CommandInfo(tags = {Tags.GUILD_ONLY})
public class Color extends Command {

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
                    LOGGER.warn("Error resolving color " + string, e);
                }
            }
        }

        return color;
    }

    @Override
    public void run() {
        IGuild guild = EVENT.getGuild();
        IUser author = EVENT.getAuthor();

        List<IRole> roles = guild.getRolesByName("color-" + author.getStringID());
        if (ARGUMENTS.isEmpty()) {
            if (roles.isEmpty()) {
                EVENT.reply("You don't have a name color. Use " + BanterBot4J.BOT_PREFIX + "color name OR hexadecimal code to assign one.");
                return;
            }

            IRole role = roles.get(0);
            List<IUser> usersByRole = guild.getUsersByRole(role);
            try {
                author.removeRole(role);
            } catch (MissingPermissionsException e) {
                if (e.getMissingPermissions() == null) {
                    EVENT.reply("I can't remove your name color.\n" +
                                "My highest role with the permission to modify roles has a lower position in the role list than your color role.");
                    return;
                }
                String missingPermissions = e.getMissingPermissions().stream().map(Permissions::name).collect(Collectors.joining(", "));
                EVENT.reply("I don't have permission to change your name color.\n" +
                            "Missing permissions: " + missingPermissions);
                return;
            }
            usersByRole.remove(author);
            if (usersByRole.isEmpty()) {
                role.delete();
            }
            String hexCode = String.format("#%06x", role.getColor().getRGB() & 0x00FFFFFF).toUpperCase();
            EVENT.reply("Removed your name color. It was " + hexCode);
            return;
        }

        String requestedColor = ARGUMENTS.get(0);
        java.awt.Color color = resolve(requestedColor);
        if (color == null) {
            EVENT.reply("Invalid color. Make sure it is a hexadecimal string (0000FF) or a simple color like red.");
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
                EVENT.reply("I don't have permission to change your name color.\n" +
                            "Missing permissions: " + missingPermissions);
                return;
            }

            EVENT.reply("Changed your name color to " + requestedColor);
            return;
        } else {
            IRole role = roles.get(0);
            String oldHexCode = String.format("#%06x", role.getColor().getRGB() & 0x00FFFFFF).toUpperCase();

            try {
                role.changeColor(color);
            } catch (MissingPermissionsException e) {
                if (e.getMissingPermissions() == null) {
                    EVENT.reply("I can't modify your name color.\n" +
                                "My highest role with the permission to modify roles has a lower position in the role list than your color role.");
                    return;
                }
                String missingPermissions = e.getMissingPermissions().stream().map(Permissions::name).collect(Collectors.joining(", "));
                EVENT.reply("I don't have permission to change your name color.\n" +
                            "Missing permissions: " + missingPermissions);
                return;
            }

            EVENT.reply("Changed your name color to " + requestedColor + ". Your old name color's hex code was " + oldHexCode);
            return;
        }
    }

}
