package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.guild.GuildUpdateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.util.Snowflake;

/**
 * Created by DrSmugleaf on 16/05/2017.
 */
@Table(name = "guilds")
public class DiscordGuild extends Model<DiscordGuild> {

    @Column(name = "id")
    @Column.Id
    public Long id;

    public DiscordGuild(Long id) {
        this.id = id;
    }

    private DiscordGuild() {}

    public Guild guild() {
        return BanterBot4J
                .CLIENT
                .getGuildById(Snowflake.of(id))
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("No Discord guild found with id " + id));
    }

    @EventListener(GuildUpdateEvent.class)
    public static void handle(GuildUpdateEvent event) {
        Runnable runnable = () -> {
            long id = event.getCurrent().getId().asLong();
            DiscordGuild guild = new DiscordGuild(id);
            guild.createIfNotExists();
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
