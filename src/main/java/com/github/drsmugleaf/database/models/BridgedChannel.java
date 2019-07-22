package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import com.github.drsmugleaf.translator.Languages;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

/**
 * Created by DrSmugleaf on 19/01/2018.
 */
@Table(name = "bridged_channels")
public class BridgedChannel extends Model<BridgedChannel> {

    @Column(name = "channel")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    @Nullable
    public DiscordChannel discordChannel;

    @Column(name = "channel_language")
    @Nullable
    public Languages channelLanguage;

    @Column(name = "bridged_channel")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    @Nullable
    public DiscordChannel bridgedChannel;

    @Column(name = "bridged_language")
    @Nullable
    public Languages bridgedLanguage;

    public BridgedChannel(@Nullable Long channelID, @Nullable Long bridgedID) {
        discordChannel = new DiscordChannel(channelID);
        bridgedChannel = new DiscordChannel(bridgedID);
    }

    private BridgedChannel() {}

    private static TextChannel from(DiscordChannel channel) {
        return Mono
                .justOrEmpty(channel)
                .map(DiscordChannel::getId)
                .map(Snowflake::of)
                .flatMap(BanterBot4J.CLIENT::getChannelById)
                .cast(TextChannel.class)
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("No text channel found with id " + channel.id));
    }

    @Nullable
    public DiscordChannel getDiscordChannel() {
        return discordChannel;
    }

    public BridgedChannel setDiscordChannel(@Nullable DiscordChannel discordChannel) {
        this.discordChannel = discordChannel;
        return this;
    }

    @Nullable
    public Languages getChannelLanguage() {
        return channelLanguage;
    }

    public BridgedChannel setChannelLanguage(@Nullable Languages channelLanguage) {
        this.channelLanguage = channelLanguage;
        return this;
    }

    @Nullable
    public DiscordChannel getBridgedChannel() {
        return bridgedChannel;
    }

    public BridgedChannel setBridgedChannel(@Nullable DiscordChannel bridgedChannel) {
        this.bridgedChannel = bridgedChannel;
        return this;
    }

    @Nullable
    public Languages getBridgedLanguage() {
        return bridgedLanguage;
    }

    public BridgedChannel setBridgedLanguage(@Nullable Languages bridgedLanguage) {
        this.bridgedLanguage = bridgedLanguage;
        return this;
    }

    public TextChannel channel() {
        return from(discordChannel);
    }

    public TextChannel bridged() {
        return from(bridgedChannel);
    }

}
