package com.github.drsmugleaf.database.model;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;

/**
 * Created by DrSmugleaf on 11/05/2018.
 */
@Table(name = "quotes")
public class Quote extends Model<Quote> {

    @Column(name = "id")
    @Column.Id
    @Column.AutoIncrement
    @Nullable
    public Long id;

    @Column(name = "content")
    @Nullable
    public String content;

    @Column(name = "guild")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    @Nullable
    public DiscordGuild guild;

    @Column(name = "submitter")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    @Nullable
    public DiscordUser submitter;

    @Column(name = "submitted")
    @Nullable
    public Long date;

    public Quote(@Nullable String content, @Nullable Long guild, @Nullable Long submitter, @Nullable Long date) {
        this.content = content;
        this.guild = new DiscordGuild(guild);
        this.submitter = new DiscordUser(submitter);
        this.date = date;
    }

    public Quote(@Nullable Long id) {
        this.id = id;
    }

    private Quote() {}

    @Nullable
    public Long getId() {
        return id;
    }

}
