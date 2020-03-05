package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.GuildCommand;
import com.github.drsmugleaf.commands.api.converter.transformer.TransformerSet;
import com.github.drsmugleaf.database.model.BridgedChannel;
import com.github.drsmugleaf.translator.Languages;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        permissions = {Permission.MANAGE_CHANNELS},
        description = "Bridge two Discord channels to send and translate messages between them"
)
public class Bridge extends GuildCommand {

    @Argument(position = 1, examples = "general")
    private TextChannel firstChannel;

    @Argument(position = 2, examples = "en")
    private Languages firstLanguage;

    @Argument(position = 3, examples = "spanish-channel")
    private TextChannel secondChannel;

    @Argument(position = 4, examples = "es")
    private Languages secondLanguage;

    @Override
    public void run() {
        BridgedChannel firstBridgedChannel = new BridgedChannel(firstChannel.getId().asLong(), secondChannel.getId().asLong());
        BridgedChannel secondBridgedChannel = new BridgedChannel(secondChannel.getId().asLong(), firstChannel.getId().asLong());

        firstBridgedChannel.channelLanguage = firstLanguage;
        firstBridgedChannel.bridgedLanguage = secondLanguage;

        secondBridgedChannel.channelLanguage = secondLanguage;
        secondBridgedChannel.bridgedLanguage = firstLanguage;

        firstBridgedChannel.save();
        secondBridgedChannel.save();

        reply(
                "Bridged together channels " + firstChannel.getName() +
                " with language " + firstLanguage.getName() +
                " and " + secondChannel.getName() +
                " with language " + secondLanguage.getName()
        ).subscribe();
    }

    @Override
    public TransformerSet getTransformers() {
        return TransformerSet.of(
                TextChannel.class, (s, e) -> e
                        .getGuild()
                        .flatMapMany(Guild::getChannels)
                        .filter(channel -> channel.getName().equalsIgnoreCase(s) && channel instanceof TextChannel)
                        .cast(TextChannel.class)
                        .blockFirst(),
                Languages.class, (s, e) -> Languages.getLanguage(s)
        );
    }

}
