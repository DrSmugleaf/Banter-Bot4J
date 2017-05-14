package com.github.drsmugbrain.commands;

import com.github.drsmugbrain.Database;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
public class Admin {

    public static void blacklist(MessageReceivedEvent event, List<String> args) {
        EnumSet<Permissions> permissions = event.getAuthor().getPermissionsForGuild(event.getGuild());

        if(permissions.contains(Permissions.KICK) || permissions.contains(Permissions.BAN)) {
            Connection conn = Database.conn;
            PreparedStatement stmt = null;
        }
    }

}
