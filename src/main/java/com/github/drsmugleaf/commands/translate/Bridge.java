package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import com.github.drsmugleaf.database.models.BridgedChannel;
import com.github.drsmugleaf.translator.Languages;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
public class Bridge extends Command {

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        if (event.ARGS.isEmpty()) {
            event.reply("You didn't provide any channels or languages.\n" +
                        "Usage: " + BOT_PREFIX + "bridge channel1 language1 channel2 language2");
            return;
        }

        if (event.ARGS.size() < 4) {
            event.reply("You didn't provide enough arguments.\n" +
                        "Usage: " + BOT_PREFIX + "bridge channel1 language1 channel2 language2");
            return;
        }

        IGuild guild = event.getGuild();
        List<IChannel> firstChannelList = guild.getChannelsByName(event.ARGS.get(0));
        if (firstChannelList.isEmpty()) {
            event.reply("Couldn't find any channels with name " + event.ARGS.get(0));
            return;
        }

        IChannel firstChannel = firstChannelList.get(0);

        Languages firstLanguage = Languages.getLanguage(event.ARGS.get(1));
        if (firstLanguage == null) {
            event.reply("Couldn't find any languages with name " + event.ARGS.get(1));
            return;
        }

        List<IChannel> secondChannelList = guild.getChannelsByName(event.ARGS.get(2));
        if (secondChannelList.isEmpty()) {
            event.reply("Couldn't find any channels with name " + event.ARGS.get(2));
            return;
        }

        IChannel secondChannel = secondChannelList.get(0);

        Languages secondLanguage = Languages.getLanguage(event.ARGS.get(3));
        if (secondLanguage == null) {
            event.reply("Couldn't find any languages with name " + event.ARGS.get(3));
            return;
        }

        BridgedChannel firstBridgedChannel = new BridgedChannel(firstChannel.getLongID(), secondChannel.getLongID());
        BridgedChannel secondBridgedChannel = new BridgedChannel(secondChannel.getLongID(), firstChannel.getLongID());

        firstBridgedChannel.channelLanguage = firstLanguage;
        firstBridgedChannel.bridgedLanguage = secondLanguage;

        secondBridgedChannel.channelLanguage = secondLanguage;
        secondBridgedChannel.bridgedLanguage = firstLanguage;

        firstBridgedChannel.save();
        secondBridgedChannel.save();

        event.reply(
                "Bridged together channels " + firstChannel.getName() +
                " with language " + firstLanguage.getName() +
                " and " + secondChannel.getName() +
                " with language " + secondLanguage.getName()
        );
    }

}
