package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.database.models.BridgedChannel;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
public class Unbridge extends Command {

    @Override
    public void run() {
        if (ARGUMENTS.isEmpty()) {
            EVENT.reply("You didn't provide a channel name.");
            return;
        }

        List<IChannel> channels = EVENT.getGuild().getChannelsByName(ARGUMENTS.get(0));
        if (channels.isEmpty()) {
            EVENT.reply("No channels found with name " + ARGUMENTS.get(0));
            return;
        }

        IChannel channel = channels.get(0);
        BridgedChannel bridgedChannel1 = new BridgedChannel(channel.getLongID(), null);
        BridgedChannel bridgedChannel2 = new BridgedChannel(null, channel.getLongID());
        bridgedChannel1.delete();
        bridgedChannel2.delete();

        EVENT.reply("Unbridged all channels bridged with " + channel.getName());
    }

}
