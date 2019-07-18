package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;

/**
 * Created by DrSmugleaf on 17/05/2017.
 */
@Table(name = "members")
public class DiscordMember extends Model<DiscordMember> {

    @Column(name = "user_id")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    @Nullable
    public DiscordUser user;

    @Column(name = "guild_id")
    @Column.Id
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    @Nullable
    public DiscordGuild guild;

    @Column(name = "is_blacklisted", defaultValue = "false")
    @Nullable
    public Boolean isBlacklisted;

    public DiscordMember(@Nullable Long userID, @Nullable Long guildID, @Nullable Boolean isBlacklisted) {
        user = new DiscordUser(userID);
        guild = new DiscordGuild(guildID);
        this.isBlacklisted = isBlacklisted;
    }

    private DiscordMember() {}

}
