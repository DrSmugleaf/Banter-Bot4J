package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Table;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
@Table(name = "channels")
public class Channel extends Model<Channel> {

    @Column(name = "id")
    @Column.Id
    public Long id;

    public Channel(Long id) {
        this.id = id;
    }

    private Channel() {
    }

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            for (IChannel iChannel : event.getClient().getChannels()) {
                Channel channel = new Channel(iChannel.getLongID());
                channel.createIfNotExists();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
