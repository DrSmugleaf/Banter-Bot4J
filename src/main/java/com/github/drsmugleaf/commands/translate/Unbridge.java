package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import com.github.drsmugleaf.database.models.BridgedChannel;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
public class Unbridge extends Command {

    @Override
    protected void run(@Nonnull CommandReceivedEvent event) {
        if (event.ARGS.isEmpty()) {
            event.reply("You didn't provide a channel name.");
            return;
        }

        List<IChannel> channels = event.getGuild().getChannelsByName(event.ARGS.get(0));
        if (channels.isEmpty()) {
            event.reply("No channels found with name " + event.ARGS.get(0));
            return;
        }

        IChannel channel = channels.get(0);
        BridgedChannel bridgedChannel1 = new BridgedChannel(channel.getLongID(), null);
        BridgedChannel bridgedChannel2 = new BridgedChannel(null, channel.getLongID());
        bridgedChannel1.delete();
        bridgedChannel2.delete();

        event.reply("Unbridged all channels bridged with " + channel.getName());
    }

}
