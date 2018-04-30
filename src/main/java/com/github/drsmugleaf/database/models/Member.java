package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.*;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Created by DrSmugleaf on 17/05/2017.
 */
@Table(name = "members")
public class Member extends Model<Member> {

    @Column(name = "user_id")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public User user;

    @Column(name = "guild_id")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public Guild guild;

    @Column(name = "is_blacklisted", defaultValue = "false")
    public Boolean isBlacklisted;

    public Member(Long userID, Long guildID, Boolean isBlacklisted) {
        user = new User(userID);
        guild = new Guild(guildID);
        this.isBlacklisted = isBlacklisted;
    }

    public Member(Long userID, Long guildID) {
        this(userID, guildID, null);
    }

    private Member() {}

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            event.getClient().getGuilds().forEach(guild -> guild.getUsers().forEach(user -> {
                Long guildID = guild.getLongID();
                Long userID = user.getLongID();
                Member memberModel = new Member(userID, guildID, false);
                memberModel.createIfNotExists();
            }));
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }


}
