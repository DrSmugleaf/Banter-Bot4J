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
@CommandInfo(name = "quote delete", aliases = {"quotedelete", "quotedel", "quote remove", "quoteremove", "quoterem"})
public class QuoteDelete extends Command {

    protected QuoteDelete(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
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

        Quote quoteToDelete = new Quote(id);

        List<Quote> quotes = quoteToDelete.get();
        if (quotes.isEmpty()) {
            event.reply("No quote was found with id " + id);
            return;
        }

        Quote fullQuote = quotes.get(0);
        IUser messageAuthor = event.getAuthor();
        IUser quoteAuthor = fullQuote.submitter.user();
        IGuild quoteGuild = fullQuote.guild.guild();
        if (messageAuthor != quoteAuthor && !isOwner(messageAuthor)) {
            String quoteAuthorName = quoteAuthor.getDisplayName(quoteGuild);
            String quoteAuthorDiscriminator = quoteAuthor.getDiscriminator();
            quoteAuthorName += "#" + quoteAuthorDiscriminator;
            event.reply("You don't have permission to delete that quote because you didn't create it. The submitter was " + quoteAuthorName);
            return;
        }

        quoteToDelete.delete();

        event.reply("Deleted quote #" + id);
    }

}
