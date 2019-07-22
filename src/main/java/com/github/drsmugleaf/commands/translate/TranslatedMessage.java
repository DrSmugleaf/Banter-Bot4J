package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.database.models.BridgedChannel;
import com.github.drsmugleaf.translator.API;
import com.github.drsmugleaf.translator.Languages;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Attachment;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/06/2018
 */
public class TranslatedMessage {

    private final Message MESSAGE;
    private final Map<BridgedChannel, Message> MESSAGES_SENT = new HashMap<>();

    public TranslatedMessage(MessageCreateEvent event) {
        MESSAGE = event.getMessage();
    }

    private Map<BridgedChannel, String> getTranslations() {
        Map<BridgedChannel, String> translations = new HashMap<>();
        List<BridgedChannel> bridgedChannels = new BridgedChannel(MESSAGE.getChannelId().asLong(), null).get();

        for (BridgedChannel bridgedChannel : bridgedChannels) {
            Languages channelLanguage = bridgedChannel.channelLanguage;
            Languages bridgedLanguage = bridgedChannel.bridgedLanguage;

            MESSAGE
                    .getContent()
                    .ifPresent(message -> {
                        String translation = API.translate(channelLanguage, bridgedLanguage, message);
                        if (translation == null) {
                            return;
                        }

                        translation = formatMessage(translation);
                        translations.put(bridgedChannel, translation);
                    });
        }

        return translations;
    }

    private void sendTranslation(BridgedChannel bridgedChannel, String translation) {
        bridgedChannel
                .bridged()
                .createMessage(translation)
                .subscribe(message -> MESSAGES_SENT.put(bridgedChannel, message));
    }

    void sendTranslations() {
        Map<BridgedChannel, String> translations = getTranslations();
        for (Map.Entry<BridgedChannel, String> entry : translations.entrySet()) {
            BridgedChannel channel = entry.getKey();
            String translation = entry.getValue();
            sendTranslation(channel, translation);
        }
    }

    void updateTranslations(Message editedMessage) {
        for (Map.Entry<BridgedChannel, Message> entry : MESSAGES_SENT.entrySet()) {
            BridgedChannel bridgedChannel = entry.getKey();
            Message message = entry.getValue();

            Languages channelLanguage = bridgedChannel.channelLanguage;
            Languages bridgedLanguage = bridgedChannel.bridgedLanguage;

            editedMessage.getContent().ifPresent(content -> {
                String translation = API.translate(channelLanguage, bridgedLanguage, content);
                if (translation == null) {
                    return;
                }

                translation = formatMessage(translation);
                String finalTranslation = translation;
                message.edit(spec -> spec.setContent(finalTranslation));
            });
        }
    }

    Flux<Void> delete() {
        return Flux
                .fromIterable(MESSAGES_SENT.entrySet())
                .map(Map.Entry::getValue)
                .flatMap(Message::delete);
    }

    private String formatMessage(@Nullable final String translation) {
        return MESSAGE
                .getAuthorAsMember()
                .map(Member::getDisplayName)
                .map(name -> "**" + name + "**: " + (translation == null ? "" : translation))
                .map(content -> {
                    StringBuilder message = new StringBuilder(content);
                    for (Attachment attachment : MESSAGE.getAttachments()) {
                        message
                                .append("\n")
                                .append(attachment.getUrl());
                    }

                    return message.toString();
                })
                .map(content -> content
                        .replace("@everyone", "@ everyone")
                        .replace("@here", "@ here")
                )
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Error formatting translation " + translation));
    }

}
