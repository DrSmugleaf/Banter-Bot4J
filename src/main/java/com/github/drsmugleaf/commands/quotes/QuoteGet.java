package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.Quote;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(name = "quote", aliases = {"quote get", "quoteget"})
public class QuoteGet extends Command {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss zzz");

    protected QuoteGet(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        long id;
        try {
            id = Long.parseLong(ARGS.toString());
        } catch (NumberFormatException e) {
            EVENT.reply("Invalid command format. Example: `" + BOT_PREFIX + "quote 1`");
            return;
        }

        Quote quote = new Quote(id);
        List<Quote> quotes = quote.get();

        if (quotes.isEmpty()) {
            EVENT.reply("No quote was found with id " + id);
            return;
        }

        quote = quotes.get(0);
        IUser quoteAuthor = quote.submitter.user();
        IGuild quoteGuild = quote.guild.guild();
        String quoteAuthorName = quoteAuthor.getDisplayName(quoteGuild);
        String quoteAuthorDiscriminator = quoteAuthor.getDiscriminator();
        quoteAuthorName += "#" + quoteAuthorDiscriminator;
        if (quote.content.isEmpty()) {
            EVENT.reply("Quote #" + quote.id + " was deleted by " + quoteAuthorName + " or one of the bot owners.");
            return;
        }

        Date date = new Date(quote.date);
        String formattedDate = DATE_FORMAT.format(date);
        Command.sendMessage(
                EVENT.getChannel(),
                "**Quote #" + quote.id + ", submitted by " + quoteAuthorName + " on " + formattedDate + "**\n" + quote.content
        );
    }

}
