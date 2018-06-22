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

    @Column(name = "submitter")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public User submitter;

    @Column(name = "guild")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public Guild guild;

    @Column(name = "submitted")
    public Long date;

    public Quote(String content, Long submitter, Long guild) {
        this.content = content;
        this.submitter = new User(submitter);
        this.guild = new Guild(guild);
        date = System.currentTimeMillis();
    }

    private Quote() {}

}
