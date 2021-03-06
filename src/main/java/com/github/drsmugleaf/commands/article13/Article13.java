package com.github.drsmugleaf.commands.article13;

import com.github.drsmugleaf.article13.entities.Country;
import com.github.drsmugleaf.article13.entities.Party;
import com.github.drsmugleaf.article13.vote.Decision;
import com.github.drsmugleaf.article13.vote.Vote;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import com.github.drsmugleaf.commands.api.converter.transformer.TransformerSet;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
@CommandInfo(description = "Results from the EU Article 13 vote, sorted by country, party and vote")
public class Article13 extends Command {

    @Argument(position = 1, examples = "spain")
    private Country country;

    @Argument(position = 2, examples = "for")
    private Decision decision;

    @Argument(position = 3, examples = "final")
    private Vote vote;

    @Override
    public void run() {
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

        reply(response.toString()).subscribe();
    }

    @Override
    public TransformerSet getTransformers() {
        return TransformerSet.of(
                Country.class, (s, e) -> Country.getCountry(s),
                Decision.class, (s, e) -> Decision.from(s),
                Vote.class, (s, e) -> Vote.getVote(s)
        );
    }

}
