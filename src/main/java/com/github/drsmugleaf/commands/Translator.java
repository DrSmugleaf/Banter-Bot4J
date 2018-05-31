package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.commands.api.Tags;
import com.github.drsmugleaf.database.models.BridgedChannel;
import com.github.drsmugleaf.translator.API;
import com.github.drsmugleaf.translator.Languages;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class Translator {

    @CommandInfo(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
    public static void bridge(@Nonnull CommandReceivedEvent event) {
        if (event.ARGS.isEmpty()) {
            event.reply("You didn't provide any channels or languages.\n" +
                        "Usage: " + BanterBot4J.BOT_PREFIX + "bridge channel1 language1 channel2 language2");
            return;
        }

        if (event.ARGS.size() < 4) {
            event.reply("You didn't provide enough arguments.\n" +
                        "Usage: " + BanterBot4J.BOT_PREFIX + "bridge channel1 language1 channel2 language2");
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

    @CommandInfo(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
    public static void unbridge(@Nonnull CommandReceivedEvent event) {
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

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        IUser author = event.getAuthor();
        if (author.isBot()) {
            return;
        }

        IChannel channel = event.getChannel();
        List<BridgedChannel> bridgedChannelList = new BridgedChannel(channel.getLongID()).get();
        if (bridgedChannelList.isEmpty()) {
            return;
        }

        String authorName = author.getDisplayName(event.getGuild());
        for (BridgedChannel bridgedChannel : bridgedChannelList) {
            Languages channelLanguage = bridgedChannel.channelLanguage;
            IChannel bridged = event.getClient().getChannelByID(bridgedChannel.bridged.id);
            Languages bridgedLanguage = bridgedChannel.bridgedLanguage;
            String translation = API.translate(channelLanguage.getCode(), bridgedLanguage.getCode(), event.getMessage().getFormattedContent());
            CommandReceivedEvent.sendMessage(bridged, "**" + authorName + "**: " + translation);
        }
    }

}
