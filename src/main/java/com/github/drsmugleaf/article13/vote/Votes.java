package com.github.drsmugleaf.article13.vote;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/04/2019
 */
public class Votes {

    @Nonnull
    private final Vote VOTE;

    @Nonnull
    private final ImmutableMap<Decision, Result> RESULTS;

    public Votes(@Nonnull Vote vote, @Nonnull Map<Decision, Integer> results) {
        VOTE = vote;
        RESULTS = ImmutableMap.copyOf(Result.from(results));
    }

    @Nonnull
    public Vote getVote() {
        return VOTE;
    }

    @Nonnull
    public ImmutableMap<Decision, Result> getResults() {
        return RESULTS;
    }

    @Nonnull
    public Result getResult(@Nonnull Decision decision) {
        return getResults().get(decision);
    }

}
