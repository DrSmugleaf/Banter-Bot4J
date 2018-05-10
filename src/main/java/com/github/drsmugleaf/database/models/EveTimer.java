package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.annotations.Relation;
import com.github.drsmugleaf.database.api.annotations.RelationTypes;
import com.github.drsmugleaf.database.api.annotations.Table;

/**
 * Created by DrSmugleaf on 02/05/2018.
 */
@Table(name = "eve_timers")
public class EveTimer extends Model<EveTimer> {

    @Column(name = "channel")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public Channel channel;

    @Column(name = "structure")
    @Column.Id
    public String structure;

    @Column(name = "system")
    @Column.Id
    public String system;

    @Column(name = "date")
    public Long date;

    @Column(name = "submitter")
    @Relation(type = RelationTypes.ManyToOne, columnName = "id")
    public User submitter;

    public EveTimer(Channel channel, String structure, String system, Long date, Long submitter) {
        this.channel = channel;
        this.structure = structure;
        this.system = system;
        this.date = date;
        this.submitter = new User(submitter);
    }

    private EveTimer() {}

}
