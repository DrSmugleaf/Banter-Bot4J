package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.guild.GuildUpdateEvent;
import discord4j.core.object.entity.Entity;
import discord4j.core.object.util.Snowflake;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
@Table(name = "guild_channels")
public class GuildChannel extends Model<GuildChannel> {

    @Column(name = "channel_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    public DiscordChannel channel;

    @Column(name = "guild_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    public DiscordGuild guild;

    public GuildChannel(Long channelID, Long guildID) {
        channel = new DiscordChannel(channelID);
        guild = new DiscordGuild(guildID);
    }

    private GuildChannel() {}

    @EventListener(GuildUpdateEvent.class)
    public static void handle(GuildUpdateEvent event) {
        Runnable runnable = () -> event
                .getCurrent()
                .getChannels()
                .map(Entity::getId)
                .map(Snowflake::asLong)
                .map(id -> new GuildChannel(id, event.getCurrent().getId().asLong()))
                .subscribe(Model::createIfNotExists);

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
