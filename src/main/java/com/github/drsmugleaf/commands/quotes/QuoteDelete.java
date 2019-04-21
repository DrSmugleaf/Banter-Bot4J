package com.github.drsmugleaf.commands.quotes;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.database.models.Quote;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

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
        }
)
public class QuoteDelete extends Command {

    @Override
    public void run() {
        long id;
        try {
            id = Long.parseLong(ARGUMENTS.toString());
        } catch (NumberFormatException e) {
            EVENT.reply("Invalid command format. Example: `" + BanterBot4J.BOT_PREFIX + "quote 1`");
            return;
        }

        Quote quote = new Quote(id);

        List<Quote> quotes = quote.get();
        if (quotes.isEmpty()) {
            EVENT.reply("No quote was found with id " + id);
            return;
        }

        quote = quotes.get(0);
        IUser messageAuthor = EVENT.getAuthor();
        IUser quoteAuthor = quote.submitter.user();
        IGuild quoteGuild = quote.guild.guild();
        if (messageAuthor != quoteAuthor && !isOwner(messageAuthor)) {
            String quoteAuthorName = quoteAuthor.getDisplayName(quoteGuild);
            String quoteAuthorDiscriminator = quoteAuthor.getDiscriminator();
            quoteAuthorName += "#" + quoteAuthorDiscriminator;
            EVENT.reply("You don't have permission to delete that quote because you didn't create it. The submitter was " + quoteAuthorName);
            return;
        }

        quote.content = "";
        quote.save();

        EVENT.reply("Deleted quote #" + id);
    }

}
