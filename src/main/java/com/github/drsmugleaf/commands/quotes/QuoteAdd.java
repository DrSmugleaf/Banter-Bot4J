package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.Quote;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(name = "quote add", aliases = "quoteadd")
public class QuoteAdd extends Command {

    protected QuoteAdd(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (ARGS.isEmpty()) {
            EVENT.reply("You didn't write anything to add as a quote. Example: `" + BOT_PREFIX + "quote add test`");
            return;
        }

        String content = ARGS.toString();
        Long authorID = EVENT.getAuthor().getLongID();
        Long guildID = EVENT.getGuild().getLongID();
        Long date = EVENT.getMessage().getCreationDate().toEpochMilli();

        Quote quote = new Quote(content, authorID, guildID, date);

        quote.createIfNotExists();

        EVENT.reply("Created quote #" + quote.id + ". Type quote `" + BOT_PREFIX + "quote " + quote.id + "` to see it.");
    }

}
