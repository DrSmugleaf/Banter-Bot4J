package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.*;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import com.github.drsmugleaf.translator.Languages;

/**
 * Created by DrSmugleaf on 19/01/2018.
 */
@Table(name = "bridged_channels")
public class BridgedChannel extends Model<BridgedChannel> {

    @Column(name = "channel")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public Channel channel;

    @Column(name = "channel_language")
    public Languages channelLanguage;

    @Column(name = "bridged_channel")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public Channel bridged;

    @Column(name = "bridged_language")
    public Languages bridgedLanguage;

    public BridgedChannel(Long channelID, Long bridgedID) {
        channel = new Channel(channelID);
        bridged = new Channel(bridgedID);
    }

    public BridgedChannel(Long channelID) {
        channel = new Channel(channelID);
    }

    private BridgedChannel() {}

}
