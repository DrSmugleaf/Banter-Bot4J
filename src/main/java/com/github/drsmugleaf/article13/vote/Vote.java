package com.github.drsmugleaf.article13.vote;

import com.github.drsmugleaf.article13.csv.Sheet;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
public class Vote {

    @Nonnull
    private static final ImmutableMap<String, Vote> VOTES = parseVotes();

    @Nonnull
    private final String NAME;

    private Vote(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    private static ImmutableMap<String, Vote> parseVotes() {
        Map<String, Vote> votes = new HashMap<>();

        List<Sheet> sheets = Sheet.getVoteSheets();
        for (String header : sheets.get(0).getHeaders()) {
            votes.put(header, new Vote(header));
        }

        return ImmutableMap.copyOf(votes);
    }

    @Nonnull
    public static ImmutableMap<String, Vote> getVotes() {
        return VOTES;
    }

    @Nonnull
    public static Vote getVote(@Nonnull String name) {
        return VOTES.get(name);
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
