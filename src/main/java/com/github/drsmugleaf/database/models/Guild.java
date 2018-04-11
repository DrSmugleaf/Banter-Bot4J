package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.Column;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.Table;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Created by DrSmugleaf on 16/05/2017.
 */
@Table(name = "guilds")
public class Guild extends Model<Guild> {

    @Column(name = "id")
    @Column.Id
    public Long id;

    public Guild(Long id) {
        this.id = id;
    }

    private Guild() {}

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            event.getClient().getGuilds().forEach(guild -> {
                Long guildID = guild.getLongID();
                Guild guildModel = new Guild(guildID);
                guildModel.createIfNotExists();
            });
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
