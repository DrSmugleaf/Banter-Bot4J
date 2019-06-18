package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.channel.TextChannelCreateEvent;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
@Table(name = "channels")
public class DiscordChannel extends Model<DiscordChannel> {

    @Column(name = "id")
    @Column.Id
    public Long id;

    public DiscordChannel(Long id) {
        this.id = id;
    }

    private DiscordChannel() {}

    @EventListener(TextChannelCreateEvent.class)
    public static void handle(TextChannelCreateEvent event) {
        Runnable runnable = () -> {
            long id = event.getChannel().getId().asLong();
            DiscordChannel channel = new DiscordChannel(id);
            channel.createIfNotExists();
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
