package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Database;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
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

    @EventListener(GuildCreateEvent.class)
    public static void handle(GuildCreateEvent event) {
        Runnable runnable = () -> {
            long guildId = event.getGuild().getId().asLong();
            DiscordGuild guild = new DiscordGuild(guildId);
            guild.createIfNotExists();

            Database.LOGGER.info("Created guild with id " + guildId);

            event
                    .getGuild()
                    .getMembers()
                    .map(User::getId)
                    .map(Snowflake::asLong)
                    .map(userId -> new DiscordUser(guildId))
                    .doOnNext(user -> {
                        user.createIfNotExists();

                        Database.LOGGER.info("Created user with id " + guildId);
                    })
                    .map(user -> new DiscordMember(user.id, guildId, false))
                    .subscribe(member -> {
                        member.createIfNotExists();

                        Database.LOGGER.info("Created member with id " + member.user.id + " in guild " + guildId);
                    });
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
