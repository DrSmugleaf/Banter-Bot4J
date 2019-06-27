package com.github.drsmugleaf.database.models;

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
    public Long id;

    @Column(name = "content")
    public String content;

    @Column(name = "guild")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordGuild guild;

    @Column(name = "submitter")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public DiscordUser submitter;

    @Column(name = "submitted")
    public Long date;

    public Quote(Long id) {
        this.id = id;
    }

    public Quote(String content, Long guild, Long submitter, Long date) {
        this.content = content;
        this.guild = new DiscordGuild(guild);
        this.submitter = new DiscordUser(submitter);
        this.date = date;
    }

    private Quote() {
    }

}
