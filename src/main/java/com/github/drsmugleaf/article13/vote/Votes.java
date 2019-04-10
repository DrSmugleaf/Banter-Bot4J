package com.github.drsmugleaf.article13.vote;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/04/2019
 */
public class Votes {

    @Nonnull
    private final Vote VOTE;

    @Nonnull
    private final ImmutableList<Result> RESULTS;

    public Votes(@Nonnull Vote vote, @Nonnull Map<Decision, Integer> results) {
        VOTE = vote;
        RESULTS = ImmutableList.copyOf(Result.from(results));
    }

    @Nonnull
    public Vote getVote() {
        return VOTE;
    }

    @Nonnull
    public ImmutableList<Result> getResults() {
        return RESULTS;
    }

}
