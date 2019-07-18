package com.github.drsmugleaf.commands.translate;

import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.tags.Tags;
import com.github.drsmugleaf.database.models.BridgedChannel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

/**
 * Created by DrSmugleaf on 10/06/2018
 */
@CommandInfo(
        permissions = {Permission.MANAGE_CHANNELS},
        tags = {Tags.GUILD_ONLY},
        description = "Unbridge two Discord channels to stop sending and translating messages between them"
)
public class Unbridge extends Command {

    @Override
    public void run() {
        if (ARGUMENTS.isEmpty()) {
            reply("You didn't provide a channel name.").subscribe();
            return;
        }

        Flux<GuildChannel> channels = EVENT
                .getGuild()
                .flatMapMany(Guild::getChannels)
                .filter(channel -> channel.getName().equalsIgnoreCase(ARGUMENTS.get(0)))
                .filter(channel -> channel instanceof TextChannel);

        Boolean hasChannels = channels.hasElements().blockOptional().orElse(false);

        if (!hasChannels) {
            reply("No channels found with name " + ARGUMENTS.get(0)).subscribe();
            return;
        }

        channels
                .take(1)
                .map(channel -> new BridgedChannel(channel.getId().asLong(), null))
                .map(channel -> Tuples.of(channel, new BridgedChannel(null, channel.channel.id)))
                .map(tuple -> {
                    tuple.getT1().delete();
                    return tuple;
                })
                .map(tuple -> {
                    tuple.getT2().delete();
                    return tuple;
                })
                .flatMap(tuple -> reply("Unbridged all channels bridged with " + ARGUMENTS.get(0)))
                .subscribe();
    }

}
