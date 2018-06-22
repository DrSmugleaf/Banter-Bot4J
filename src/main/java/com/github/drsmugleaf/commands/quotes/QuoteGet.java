package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;
import com.github.drsmugleaf.database.models.Quote;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 21/06/2018
 */
@CommandInfo(name = "quote", aliases = {"quote get", "quoteget"})
public class QuoteGet extends Command {

    protected QuoteGet(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run(@Nonnull CommandReceivedEvent event) {
        long id;
        try {
            id = Long.parseLong(ARGS.toString());
        } catch (NumberFormatException e) {
            event.reply("Invalid command format. Example: `" + BOT_PREFIX + "quote 1`");
            return;
        }

        Quote quote = new Quote(id);
        List<Quote> quotes = quote.get();

        if (quotes.isEmpty()) {
            event.reply("No quotes found with id " + id);
            return;
        }

        quote = quotes.get(0);
        IUser quoteAuthor = quote.submitter.user();
        IGuild quoteGuild = quote.guild.guild();
        String authorName = quoteAuthor.getDisplayName(quoteGuild);
        CommandReceivedEvent.sendMessage(
                event.getChannel(),
                "**Submitted by " + authorName + ":**\n" + quote.content
        );
    }

}
