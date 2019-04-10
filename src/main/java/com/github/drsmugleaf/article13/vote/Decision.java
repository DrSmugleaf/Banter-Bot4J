package com.github.drsmugleaf.article13.vote;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public enum Decision {

    FOR("FOR"),
    AGAINST("AGAINST"),
    ABSTAINED("ABSTAINED"),
    NOT_PRESENT("NOT PRESENT");

    @Nonnull
    private static final ImmutableSet<Decision> VOTE_TYPES = ImmutableSet.copyOf(values());

    @Nonnull
    private final String NAME;

    Decision(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public static ImmutableSet<Decision> getVoteTypes() {
        return VOTE_TYPES;
    }

    @Nonnull
    public static Decision from(@Nonnull String vote) {
        for (Decision type : VOTE_TYPES) {
            if (vote.contains(type.getName())) {
                return type;
            }
        }

        throw new IllegalArgumentException("No vote type found for vote: " + vote);
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
