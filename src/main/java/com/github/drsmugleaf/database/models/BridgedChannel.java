package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import com.github.drsmugleaf.translator.Languages;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/01/2018.
 */
@Table(name = "bridged_channels")
public class BridgedChannel extends Model<BridgedChannel> {

    @Column(name = "channel")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordChannel channel;

    @Column(name = "channel_language")
    public Languages channelLanguage;

    @Column(name = "bridged_channel")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordChannel bridged;

    @Column(name = "bridged_language")
    public Languages bridgedLanguage;

    public BridgedChannel(Long channelID, Long bridgedID) {
        channel = new DiscordChannel(channelID);
        bridged = new DiscordChannel(bridgedID);
    }

    public BridgedChannel(Long channelID) {
        channel = new DiscordChannel(channelID);
    }

    private BridgedChannel() {}

    @Nonnull
    private static TextChannel from(@Nonnull DiscordChannel channel) {
        return BanterBot4J
                .CLIENT
                .getChannelById(Snowflake.of(channel.id))
                .cast(TextChannel.class)
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("No text channel found with id " + channel.id));
    }

    @Nonnull
    public TextChannel channel() {
        return from(channel);
    }

    @Nonnull
    public TextChannel bridged() {
        return from(bridged);
    }

}
