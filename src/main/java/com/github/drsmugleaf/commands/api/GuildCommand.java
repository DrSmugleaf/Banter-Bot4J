package com.github.drsmugleaf.commands.api;

import com.github.drsmugleaf.commands.api.registry.Entry;
import com.github.drsmugleaf.commands.api.registry.GuildCommandEntry;
import discord4j.core.object.entity.Member;

/**
 * Created by DrSmugleaf on 27/02/2020
 */
public abstract class GuildCommand extends Command {

    public Member MEMBER;

    protected GuildCommand() {}

    @Override
    @SuppressWarnings("unchecked")
    public Entry<? extends GuildCommand> getEntry() {
        return new GuildCommandEntry((Class<GuildCommand>) getClass());
    }

}
