package com.github.drsmugleaf.database.model;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Table;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
@Table(name = "users")
public class DiscordUser extends Model<DiscordUser> {

    @Column(name = "id")
    @Column.Id
    @Nullable
    public Long id;

    public DiscordUser(@Nullable Long id) {
        this.id = id;
    }

    private DiscordUser() {}

    public User user() {
        return Mono
                .justOrEmpty(id)
                .map(Snowflake::of)
                .flatMap(BanterBot4J.CLIENT::getUserById)
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("No Discord user found with id " + id));
    }

}
