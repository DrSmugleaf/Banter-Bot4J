package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.object.entity.Entity;
import discord4j.core.object.util.Snowflake;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
@Table(name = "guild_channels")
public class DiscordGuildChannel extends Model<DiscordGuildChannel> {

    @Column(name = "channel_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    @Nullable
    public DiscordChannel channel;

    @Column(name = "guild_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    @Nullable
    public DiscordGuild guild;

    public DiscordGuildChannel(@Nullable Long channelID, @Nullable Long guildID) {
        channel = new DiscordChannel(channelID);
        guild = new DiscordGuild(guildID);
    }

    private DiscordGuildChannel() {}

    @EventListener(GuildCreateEvent.class)
    public static void handle(GuildCreateEvent event) {
        Runnable runnable = () -> event
                .getGuild()
                .getChannels()
                .map(Entity::getId)
                .map(Snowflake::asLong)
                .map(id -> new DiscordGuildChannel(id, event.getGuild().getId().asLong()))
                .subscribe(model -> {
                    model.createIfNotExists();

                    Database.LOGGER.info(
                            String.format("Created guild channel with id %s in guild %s", model.channel.id, model.guild.id)
                    );
                });

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
