package com.github.drsmugbrain.dungeon;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;


/**
 * Created by Brian on 17/05/2017.
 */
public class DungeonEvents {
    @EventSubscriber
    public void onReaction(ReactionAddEvent event){
        Dungeon.input(event.getMessage(), event.getReaction(), event.getUser());
    }
}
