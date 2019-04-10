package com.github.drsmugleaf.article13.vote;

import javax.annotation.Nonnull;
import java.util.EnumMap;
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
    public static Map<Decision, Result> from(@Nonnull Map<Decision, Integer> map) {
        Map<Decision, Result> results = new EnumMap<>(Decision.class);

        for (Map.Entry<Decision, Integer> entry : map.entrySet()) {
            Decision decision = entry.getKey();
            Result result = from(entry);
            results.put(decision, result);
        }

        for (Decision decision : Decision.getDecisions()) {
            if (!results.containsKey(decision)) {
                results.put(decision, new Result(decision, 0));
            }
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
