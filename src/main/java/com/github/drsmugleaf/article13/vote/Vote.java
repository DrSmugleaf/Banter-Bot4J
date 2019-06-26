package com.github.drsmugleaf.article13.vote;

import com.github.drsmugleaf.article13.csv.Sheet;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import com.github.drsmugleaf.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
public class Vote {

    private static final ImmutableMap<String, Vote> VOTES = parseVotes();

    private final String NAME;

    private Vote(String name) {
        NAME = name.replaceAll("\n", " ");
    }

    private static ImmutableMap<String, Vote> parseVotes() {
        Map<String, Vote> votes = new HashMap<>();

        List<Sheet> sheets = Sheet.getVoteSheets();
        for (String header : sheets.get(0).getHeaders()) {
            if (!(header.contains("Vote") || header.contains("Pledge"))) {
                continue;
            }

            votes.put(header.replaceAll("\n", " "), new Vote(header));
        }

        return ImmutableMap.copyOf(votes);
    }

    public static ImmutableMap<String, Vote> getVotes() {
        return VOTES;
    }

    public static ImmutableSet<String> getVoteNames() {
        return getVotes().keySet();
    }

    @Nullable
    public static Vote getVote(String name) {
        name = name.toLowerCase();

        for (Vote vote : getVotes().values()) {
            if (vote.getName().toLowerCase().contains(name)) {
                return vote;
            }
        }

        return null;
    }

    public String getName() {
        return NAME;
    }

}
