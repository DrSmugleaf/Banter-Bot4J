package com.github.drsmugleaf.article13.entities;

import com.github.drsmugleaf.article13.vote.Decision;
import com.github.drsmugleaf.article13.vote.Vote;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public class Member {

    @Nonnull
    private final String FIRST_NAME;

    @Nonnull
    private final String LAST_NAME;

    @Nonnull
    private final String COUNTRY;

    @Nonnull
    private final String JURISDICTION;

    @Nonnull
    private final String POLITICAL_GROUP;

    @Nonnull
    private final ImmutableMap<Vote, Decision> VOTES;

    @Nonnull
    private final String NATIONAL_POLITICAL_GROUP;

    @Nonnull
    private final String NATIONAL_POLITICAL_GROUP_SHORT;

    protected Member(@Nonnull Map<String, String> csvLine) {
        FIRST_NAME = csvLine.get("First Name");
        LAST_NAME = csvLine.get("Last Name");
        COUNTRY = csvLine.get("Country");
        JURISDICTION = csvLine.get("Jurisdiction");
        POLITICAL_GROUP = csvLine.get("Political Group");
        VOTES = getVotes(csvLine);
        NATIONAL_POLITICAL_GROUP = csvLine.get("National Political Group");
        NATIONAL_POLITICAL_GROUP_SHORT = csvLine.get("National Political Group (Short)");
    }

    @Nonnull
    private static ImmutableMap<Vote, Decision> getVotes(@Nonnull Map<String, String> csvLine) {
        Map<Vote, Decision> votes = new HashMap<>();

        for (Map.Entry<String, String> entry : csvLine.entrySet()) {
            String header = entry.getKey();
            if (!(header.contains("Vote") || header.contains("Pledge"))) {
                continue;
            }

            String value = entry.getValue();
            Vote vote = Vote.getVote(header.replaceAll("\n", " "));
            Decision decision = Decision.from(value);
            votes.put(vote, decision);
        }

        return ImmutableMap.copyOf(votes);
    }

    @Nonnull
    public String getFirstName() {
        return FIRST_NAME;
    }

    @Nonnull
    public String getSecondName() {
        return LAST_NAME;
    }

    @Nonnull
    public Country getCountry() {
        return Country.getCountry(COUNTRY);
    }

    @Nonnull
    public String getJurisdiction() {
        return JURISDICTION;
    }

    @Nonnull
    public String getPoliticalGroup() {
        return POLITICAL_GROUP;
    }

    @Nonnull
    public ImmutableMap<Vote, Decision> getVotes() {
        return VOTES;
    }

    @Nonnull
    public String getNationalPoliticalGroup() {
        return NATIONAL_POLITICAL_GROUP;
    }

    @Nonnull
    public String getNationalPoliticalGroupShort() {
        return NATIONAL_POLITICAL_GROUP_SHORT;
    }

}
