package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.models.Quote;
import discord4j.core.object.entity.User;

import java.util.List;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(
        name = "quotedelete",
        aliases = {
                "quote delete",
                "quotedel", "quote del",
                "quoteremove", "quote remove",
                "quoterem", "quote rem"
        },
        description = "Delete a quote"
)
public class QuoteDelete extends Command {

    @Override
    public void run() {
        long id;
        try {
            id = Long.parseLong(ARGUMENTS.toString());
        } catch (NumberFormatException e) {
            reply("Invalid command format. Example: `" + BanterBot4J.BOT_PREFIX + "quote 1`").subscribe();
            return;
        }

        Quote quote = new Quote(id);

        List<Quote> quotes = quote.get();
        if (quotes.isEmpty()) {
            reply("No quote was found with id " + id).subscribe();
            return;
        }

        quote = quotes.get(0);
        Quote finalQuote = quote;
        EVENT
                .getMember()
                .ifPresent(author -> {
                    User submitter = finalQuote.submitter.user();
                    if (!author.getId().equals(submitter.getId()) && !isOwner(author)) {
                        String submitterName = submitter.getUsername() + "#" + submitter.getDiscriminator();
                        reply("You don't have permission to delete that quote because you didn't create it. The submitter was " + submitterName).subscribe();
                        return;
                    }

                    finalQuote.content = "";
                    finalQuote.save();

                    reply("Deleted quote #" + id).subscribe();
                });
    }

}
