package com.github.drsmugleaf.article13.vote;

import com.github.drsmugleaf.Nullable;
import com.google.common.collect.ImmutableSet;

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

    private static final ImmutableSet<Decision> DECISIONS = ImmutableSet.copyOf(values());

    private final String NAME;

    private final String[] ALIASES;

    Decision(String... name) {
        NAME = name[0];
        ALIASES = name;
    }

    public static ImmutableSet<Decision> getDecisions() {
        return DECISIONS;
    }

    public static Set<String> getDecisionNames() {
        return getDecisions().stream().map(Decision::getName).collect(Collectors.toSet());
    }

    @Nullable
    public static Decision from(String string) {
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

    public String getName() {
        return NAME;
    }

    public String[] getAliases() {
        return ALIASES.clone();
    }

}
