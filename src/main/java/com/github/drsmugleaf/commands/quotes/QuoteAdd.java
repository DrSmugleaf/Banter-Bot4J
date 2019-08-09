package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.models.Quote;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(
        name = "quoteadd",
        aliases = "quote add",
        description = "Create a quote"
)
public class QuoteAdd extends Command {

    @Argument(
            position = 1,
            examples = "Did you ever hear the tragedy of Darth Plagueis The Wise? " +
                    "I thought not. It's not a story the Jedi would tell you.",
            maxWords = Integer.MAX_VALUE
    )
    private String text;

    @Override
    public void run() {
        EVENT
                .getGuild()
                .map(Guild::getId)
                .map(Snowflake::asLong)
                .zipWith(
                        Mono.justOrEmpty(
                                EVENT
                                        .getMessage()
                                        .getAuthor()
                                        .map(User::getId)
                                        .map(Snowflake::asLong)
                        )
                )
                .zipWith(
                        Mono.just(
                                EVENT
                                .getMessage()
                                .getTimestamp()
                                .toEpochMilli()
                        )
                )
                .map(tuple -> Tuples.of(tuple.getT1().getT1(), tuple.getT1().getT2(), tuple.getT2()))
                .map(tuple -> new Quote(text, tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .doOnNext(Quote::createIfNotExists)
                .flatMap(quote -> reply("Created quote #" + quote.id + ". Type `" + BanterBot4J.BOT_PREFIX + "quote " + quote.id + "` to see it."))
                .subscribe();
    }

}
