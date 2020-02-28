package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.commands.api.registry.GuildCommandEntry;
import discord4j.core.object.entity.Member;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public abstract class GuildCommand extends Command {

    public Member MEMBER;

    protected GuildCommand() {}

    @Override
    public GuildCommandEntry<? extends GuildCommand> toEntry() {
        return new GuildCommandEntry<>(getClass());
    }

}
