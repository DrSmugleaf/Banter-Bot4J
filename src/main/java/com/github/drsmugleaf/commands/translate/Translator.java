package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.BridgedChannel;
import com.github.drsmugleaf.translator.API;
import com.github.drsmugleaf.translator.Languages;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
public class Translator {

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
