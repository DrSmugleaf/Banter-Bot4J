package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.*;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
@Table(name = "guild_channels")
public class GuildChannel extends Model<GuildChannel> {

    @Column(name = "channel_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    public Channel channel;

    @Column(name = "guild_id")
    @Column.Id
    @Relation(type = RelationTypes.OneToOne, columnName = "id")
    public Guild guild;

    public GuildChannel(Long channelID, Long guildID) {
        channel = new Channel(channelID);
        guild = new Guild(guildID);
    }

    private GuildChannel() {}

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            for (IGuild iGuild : event.getClient().getGuilds()) {
                for (IChannel iChannel : iGuild.getChannels()) {
                    long channelID = iChannel.getLongID();
                    long guildID = iGuild.getLongID();
                    GuildChannel guildChannel = new GuildChannel(channelID, guildID);
                    guildChannel.createIfNotExists();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
