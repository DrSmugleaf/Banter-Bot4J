package com.github.drsmugleaf.article13.vote;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public class Result implements Comparable<Result> {

    @Nonnull
    private final Decision DECISION;

    private final int VOTES;

    public Result(@Nonnull Decision decision, int votes) {
        DECISION = decision;
        VOTES = votes;
    }

    @Nonnull
    public static Result from(@Nonnull Map.Entry<Decision, Integer> entry) {
        Decision decision = entry.getKey();
        Integer votes = entry.getValue();

        return new Result(decision, votes);
    }

    @Nonnull
    public static List<Result> from(@Nonnull Map<Decision, Integer> map) {
        List<Result> results = new ArrayList<>();
        for (Map.Entry<Decision, Integer> entry : map.entrySet()) {
            Result result = from(entry);
            results.add(result);
        }

        return results;
    }

    @Nonnull
    public Decision getDecision() {
        return DECISION;
    }

    public int getVotes() {
        return VOTES;
    }

    @Override
    public int compareTo(@Nonnull Result o) {
        return Integer.compare(getVotes(), o.getVotes());
    }

}
