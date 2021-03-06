package com.github.drsmugleaf.charactersheets.character.sheet;

import com.github.drsmugleaf.charactersheets.ability.AbilitySet;
import com.github.drsmugleaf.charactersheets.stat.StatGroup;
import com.github.drsmugleaf.charactersheets.state.State;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Sheet {

    private final ImmutableMap<State, StatGroup> STATS;
    private final ImmutableMap<State, AbilitySet> ABILITIES;

    public Sheet(Map<State, StatGroup> stats, Map<State, AbilitySet> abilities) {
        STATS = ImmutableMap.copyOf(stats);
        ABILITIES = ImmutableMap.copyOf(abilities);
    }

    public Sheet() {
        this(new HashMap<>(), new HashMap<>());
    }

    public ImmutableMap<State, StatGroup> getStats() {
        return STATS;
    }

    public StatGroup getStats(State state) {
        return STATS.get(state);
    }

    public ImmutableMap<State, AbilitySet> getAbilities() {
        return ABILITIES;
    }

    public AbilitySet getAbilities(State state) {
        return ABILITIES.get(state);
    }

}
