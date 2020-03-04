package com.github.drsmugleaf.commands.quote;

import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.model.Quote;
import discord4j.core.object.entity.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(
        name = "quote",
        aliases = {"quote get", "quoteget"},
        description = "Get a quote"
)
public class QuoteGet extends Command {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

    @Argument(position = 1, examples = "1", optional = true)
    private long id;

    @Override
    public void run() {
        if (id == 0) {
            Quote quote = new Quote(null);
            id = quote.get().stream().mapToLong(Quote::getId).max().orElse(0);

            if (id == 0) {
                reply("No quotes have been added yet").subscribe();
                return;
            }
        }

        Quote quote = new Quote(id);
        List<Quote> quotes = quote.get();

        if (quotes.isEmpty()) {
            reply("No quote was found with id " + id).subscribe();
            return;
        }

        quote = quotes.get(0);
        User submitter = quote.submitter.user();
        String submitterName = submitter.getUsername() + "#" + submitter.getDiscriminator();
        if (quote.content.isEmpty()) {
            reply("Quote #" + quote.id + " was deleted by " + submitterName + " or one of the bot owners.").subscribe();
            return;
        }

        Long quoteDate = quote.date;
        Instant date = Instant.ofEpochMilli(quoteDate);
        String formattedDate = DATE_FORMAT.format(date);
        Quote finalQuote = quote;
        reply("**Quote #" + finalQuote.id + ", submitted by " + submitterName + " on " + formattedDate + "**\n" + finalQuote.content).subscribe();
    }

}
