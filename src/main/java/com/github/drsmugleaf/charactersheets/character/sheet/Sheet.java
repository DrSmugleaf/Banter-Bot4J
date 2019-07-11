package com.github.drsmugleaf.charactersheets.character.sheet;

import com.github.drsmugleaf.charactersheets.ability.Abilities;
import com.github.drsmugleaf.charactersheets.stat.Stats;
import com.github.drsmugleaf.charactersheets.state.State;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Sheet {

    private final ImmutableMap<State, Stats> STATS;
    private final ImmutableMap<State, Abilities> ABILITIES;

    public Sheet(Map<State, Stats> stats, Map<State, Abilities> abilities) {
        STATS = ImmutableMap.copyOf(stats);
        ABILITIES = ImmutableMap.copyOf(abilities);
    }

    public ImmutableMap<State, Stats> getStats() {
        return STATS;
    }

    public Stats getStats(State state) {
        return STATS.get(state);
    }

    public ImmutableMap<State, Abilities> getAbilities() {
        return ABILITIES;
    }

    public Abilities getAbilities(State state) {
        return ABILITIES.get(state);
    }

}
