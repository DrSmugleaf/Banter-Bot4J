package com.github.drsmugleaf.article13.vote;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public enum Decision {

    FOR("FOR", "YES"),
    AGAINST("AGAINST"),
    ABSTAINED("ABSTAINED"),
    ABSENT("ABSENT", "NOT PRESENT", "-");

    @Nonnull
    private static final ImmutableSet<Decision> DECISIONS = ImmutableSet.copyOf(values());

    @Nonnull
    private final String NAME;

    @Nonnull
    private final String[] ALIASES;

    Decision(@Nonnull String... name) {
        NAME = name[0];
        ALIASES = name;
    }

    @Nonnull
    public static ImmutableSet<Decision> getDecisions() {
        return DECISIONS;
    }

    @Nonnull
    public static Set<String> getDecisionNames() {
        return getDecisions().stream().map(Decision::getName).collect(Collectors.toSet());
    }

    @Nullable
    public static Decision from(@Nonnull String string) {
        string = string.toLowerCase();

        for (Decision decision : getDecisions()) {
            for (String alias : decision.ALIASES) {
                if (string.contains(alias.toLowerCase())) {
                    return decision;
                }
            }
        }

        return null;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    @Nonnull
    public String[] getAliases() {
        return ALIASES.clone();
    }

}
