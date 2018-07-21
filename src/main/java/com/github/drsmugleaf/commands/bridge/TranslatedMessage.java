package com.github.drsmugleaf.commands.bridge;

import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.BridgedChannel;
import com.github.drsmugleaf.translator.API;
import com.github.drsmugleaf.translator.Languages;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/06/2018
 */
public class TranslatedMessage {

    @Nonnull
    final IMessage MESSAGE;

    @Nonnull
    final Map<BridgedChannel, IMessage> MESSAGES_SENT = new HashMap<>();

    public TranslatedMessage(@Nonnull MessageReceivedEvent event) {
        MESSAGE = event.getMessage();
    }

    private Map<BridgedChannel, String> getTranslations() {
        Map<BridgedChannel, String> translations = new HashMap<>();
        List<BridgedChannel> bridgedChannels = new BridgedChannel(MESSAGE.getChannel().getLongID()).get();

        for (BridgedChannel bridgedChannel : bridgedChannels) {
            Languages channelLanguage = bridgedChannel.channelLanguage;
            Languages bridgedLanguage = bridgedChannel.bridgedLanguage;
            String translation = API.translate(channelLanguage, bridgedLanguage, MESSAGE.getFormattedContent());
            if (translation == null) {
                continue;
            }

            translation = formatMessage(translation);
            translations.put(bridgedChannel, translation);
        }

        return translations;
    }

    private void sendTranslation(@Nonnull BridgedChannel bridgedChannel, @Nonnull String translation) {
        IMessage message = CommandReceivedEvent.sendMessage(bridgedChannel.bridged(), translation);
        MESSAGES_SENT.put(bridgedChannel, message);
    }

    void sendTranslations() {
        Map<BridgedChannel, String> translations = getTranslations();
        for (Map.Entry<BridgedChannel, String> entry : translations.entrySet()) {
            BridgedChannel channel = entry.getKey();
            String translation = entry.getValue();
            sendTranslation(channel, translation);
        }
    }

    void updateTranslations(@Nonnull IMessage editedMessage) {
        for (Map.Entry<BridgedChannel, IMessage> entry : MESSAGES_SENT.entrySet()) {
            BridgedChannel bridgedChannel = entry.getKey();
            IMessage message = entry.getValue();

            Languages channelLanguage = bridgedChannel.channelLanguage;
            Languages bridgedLanguage = bridgedChannel.bridgedLanguage;

            String translation = API.translate(channelLanguage, bridgedLanguage, editedMessage.getFormattedContent());
            if (translation == null) {
                continue;
            }

            translation = formatMessage(translation);
            message.edit(translation);
        }
    }

    void delete() {
        for (Map.Entry<BridgedChannel, IMessage> entry : MESSAGES_SENT.entrySet()) {
            entry.getValue().delete();
        }
    }

    @Nonnull
    String formatMessage(@Nonnull String translation) {
        IGuild guild = MESSAGE.getGuild();
        String authorName = MESSAGE.getAuthor().getDisplayName(guild);

        StringBuilder message = new StringBuilder("**" + authorName + "**: " + translation);
        for (IMessage.Attachment attachment : MESSAGE.getAttachments()) {
            message
                    .append("\n")
                    .append(attachment.getUrl());
        }

        translation = message.toString();
        translation = translation.replace("@everyone", "@ everyone");

        return translation;
    }

}
