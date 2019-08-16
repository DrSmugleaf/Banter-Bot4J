package com.github.drsmugleaf.commands.quote;

import com.github.drsmugleaf.commands.api.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.model.Quote;
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

    @Argument(position = 1, examples = "1")
    private long id;

    @Override
    public void run() {
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
