package com.github.drsmugleaf.article13.vote;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 10/04/2019
 */
public class Votes {

    private final Vote VOTE;

    private final ImmutableMap<Decision, Result> RESULTS;

    public Votes(Vote vote, Map<Decision, Integer> results) {
        VOTE = vote;
        RESULTS = ImmutableMap.copyOf(Result.from(results));
    }

    public Vote getVote() {
        return VOTE;
    }

    public ImmutableMap<Decision, Result> getResults() {
        return RESULTS;
    }

    public Result getResult(Decision decision) {
        return getResults().get(decision);
    }

}
