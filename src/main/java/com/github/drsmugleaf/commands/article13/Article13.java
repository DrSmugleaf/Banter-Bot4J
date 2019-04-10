package com.github.drsmugleaf.commands.article13;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.article13.entities.Country;
import com.github.drsmugleaf.article13.entities.Party;
import com.github.drsmugleaf.article13.vote.Decision;
import com.github.drsmugleaf.article13.vote.Vote;
import com.github.drsmugleaf.commands.api.Arguments;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
public class Article13 extends Command {

    protected Article13(@Nonnull CommandReceivedEvent event, @Nonnull Arguments args) {
        super(event, args);
    }

    @Override
    public void run() {
        if (ARGS.size() < 3) {
            EVENT.reply(
                    "Invalid arguments.\n" +
                            "**Valid countries:**\n" +
                            String.join(", ", Country.getCountryNames()) +
                            "\n**Valid decisions:**\n" +
                            String.join(", ", Decision.getDecisionNames()) +
                            "\n**Valid votes:**\n" +
                            String.join(", ", Vote.getVoteNames()) +
                            "\nExample: " + BanterBot4J.BOT_PREFIX + "article13 spain for final"
            );
            return;
        }

        String countryName = ARGS.get(0);
        Country country = Country.getCountry(countryName);
        if (country == null) {
            EVENT.reply(
                    "Invalid country name.\n" +
                            "**Valid countries:**\n" +
                            String.join(", ", Country.getCountryNames()) +
                            "\nExample: " + BanterBot4J.BOT_PREFIX + "article13 spain for final"
            );
            return;
        }

        String decisionName = ARGS.get(1);
        Decision decision = Decision.from(decisionName);
        if (decision == null) {
            EVENT.reply(
                    "Invalid decision name.\n" +
                            "**Valid decisions:**\n" +
                            String.join(", ", Decision.getDecisionNames()) +
                            "\nExample: " + BanterBot4J.BOT_PREFIX + "article13 spain for final"
            );
            return;
        }

        String voteName = ARGS.getFrom(2);
        Vote vote = Vote.getVote(voteName);
        if (vote == null) {
            EVENT.reply(
                    "Invalid vote name\n" +
                            "**Valid votes:**\n" +
                            String.join(", ", Vote.getVoteNames()) +
                            "\nExample: " + BanterBot4J.BOT_PREFIX + "article13 spain for final"
            );
            return;
        }

        List<Party> parties = country
                .getParties()
                .entrySet()
                .stream()
                .sorted(((o1, o2) -> o1.getValue().compareVote(o2.getValue(), vote, decision)))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        StringBuilder response = new StringBuilder("\n");
        for (Party party : parties) {
            String partyName = party.getName();
            int votes = party.getVotes(vote).getResult(decision).getVotes();

            response
                    .append("**")
                    .append(partyName)
                    .append("**")
                    .append(" (")
                    .append(votes)
                    .append(")\n");
        }

        EVENT.reply(response.toString());
    }

}
