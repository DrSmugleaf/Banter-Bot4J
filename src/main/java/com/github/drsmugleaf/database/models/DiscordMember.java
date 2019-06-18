package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.commands.api.EventListener;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.event.domain.guild.MemberChunkEvent;
import discord4j.core.object.entity.Member;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/05/2017.
 */
@Table(name = "members")
public class DiscordMember extends Model<DiscordMember> {

    @Column(name = "user_id")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordUser user;

    @Column(name = "guild_id")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordGuild guild;

    @Column(name = "is_blacklisted", defaultValue = "false")
    public Boolean isBlacklisted;

    public DiscordMember(Long userID, Long guildID, Boolean isBlacklisted) {
        user = new DiscordUser(userID);
        guild = new DiscordGuild(guildID);
        this.isBlacklisted = isBlacklisted;
    }

    public DiscordMember(Long userID, Long guildID) {
        this(userID, guildID, null);
    }

    private DiscordMember() {}

    @EventListener(MemberChunkEvent.class)
    public static void handle(@Nonnull MemberChunkEvent event) {
        Runnable runnable = () -> {
            for (Member member : event.getMembers()) {
                long userId = member.getId().asLong();
                long guildId = member.getGuildId().asLong();
                DiscordMember memberModel = new DiscordMember(userId, guildId, false);
                memberModel.createIfNotExists();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }


}
