package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.models.Quote;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(name = "quote add", aliases = "quoteadd")
public class QuoteAdd extends Command {

    @Override
    public void run() {
        if (ARGUMENTS.isEmpty()) {
            EVENT.reply("You didn't write anything to add as a quote. Example: `" + BanterBot4J.BOT_PREFIX + "quote add test`");
            return;
        }

        String content = ARGUMENTS.toString();
        Long authorID = EVENT.getAuthor().getLongID();
        Long guildID = EVENT.getGuild().getLongID();
        Long date = EVENT.getMessage().getCreationDate().toEpochMilli();

        Quote quote = new Quote(content, authorID, guildID, date);

        quote.createIfNotExists();

        EVENT.reply("Created quote #" + quote.id + ". Type `" + BanterBot4J.BOT_PREFIX + "quote " + quote.id + "` to see it.");
    }

}
