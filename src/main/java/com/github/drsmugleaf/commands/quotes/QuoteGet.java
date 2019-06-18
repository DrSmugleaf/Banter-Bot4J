package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.models.Quote;
import discord4j.core.object.entity.User;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(name = "quote", aliases = {"quote get", "quoteget"})
public class QuoteGet extends Command {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

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
