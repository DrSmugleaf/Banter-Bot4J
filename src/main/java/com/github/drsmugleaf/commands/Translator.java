package com.github.drsmugleaf.commands;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.models.BridgedChannel;
import com.github.drsmugleaf.translator.API;
import com.github.drsmugleaf.translator.Languages;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class Translator extends AbstractCommand {

    @Command(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
    public static void bridge(@Nonnull MessageReceivedEvent event, List<String> args) {
        if (args.isEmpty()) {
            sendMessage(event.getChannel(), "You didn't provide any channels or languages.\n" +
                                            "Usage: " + BanterBot4J.BOT_PREFIX + "bridge channel1 language1 channel2 language2");
            return;
        }

        if (args.size() < 4) {
            sendMessage(event.getChannel(), "You didn't provide enough arguments.\n" +
                                            "Usage: " + BanterBot4J.BOT_PREFIX + "bridge channel1 language1 channel2 language2");
            return;
        }

        IGuild guild = event.getGuild();
        List<IChannel> firstChannelList = guild.getChannelsByName(args.get(0));
        if (firstChannelList.isEmpty()) {
            sendMessage(event.getChannel(), "Couldn't find any channels with name " + args.get(0));
            return;
        }

        IChannel firstChannel = firstChannelList.get(0);

        Languages firstLanguage = Languages.getLanguage(args.get(1));
        if (firstLanguage == null) {
            sendMessage(event.getChannel(), "Couldn't find any languages with name " + args.get(1));
            return;
        }

        List<IChannel> secondChannelList = guild.getChannelsByName(args.get(2));
        if (secondChannelList.isEmpty()) {
            sendMessage(event.getChannel(), "Couldn't find any channels with name " + args.get(2));
            return;
        }

        IChannel secondChannel = secondChannelList.get(0);

        Languages secondLanguage = Languages.getLanguage(args.get(3));
        if (secondLanguage == null) {
            sendMessage(event.getChannel(), "Couldn't find any languages with name " + args.get(3));
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

        sendMessage(event.getChannel(),
                "Bridged together channels " + firstChannel.getName() +
                " with language " + firstLanguage.getName() +
                " and " + secondChannel.getName() +
                " with language " + secondLanguage.getName()
        );
    }

    @Command(permissions = {Permissions.MANAGE_CHANNELS}, tags = {Tags.GUILD_ONLY})
    public static void unbridge(@Nonnull MessageReceivedEvent event, List<String> args) {
        if (args.isEmpty()) {
            sendMessage(event.getChannel(), "You didn't provide a channel name.");
            return;
        }

        List<IChannel> channels = event.getGuild().getChannelsByName(args.get(0));
        if (channels.isEmpty()) {
            sendMessage(event.getChannel(), "No channels found with name " + args.get(0));
            return;
        }

        IChannel channel = channels.get(0);
        BridgedChannel.delete(channel.getLongID());

        sendMessage(event.getChannel(), "Unbridged all channels bridged with " + channel.getName());
    }

    @EventSubscriber
    public static void handle(@Nonnull MessageReceivedEvent event) {
        IChannel channel = event.getChannel();
        List<BridgedChannel> bridgedChannelList = BridgedChannel.get(channel.getLongID());
        if (bridgedChannelList.isEmpty()) {
            return;
        }

        for (BridgedChannel bridgedChannel : bridgedChannelList) {
            Languages channelLanguage = bridgedChannel.channelLanguage;
            IChannel bridged = event.getClient().getChannelByID(bridgedChannel.bridgedID);
            Languages bridgedLanguage = bridgedChannel.bridgedLanguage;
            String translation = API.translate(channelLanguage.getCode(), bridgedLanguage.getCode(), event.getMessage().getFormattedContent());
            bridged.sendMessage(translation);
        }
    }

}
