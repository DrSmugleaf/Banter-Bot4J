package com.github.drsmugleaf.charactersheets.character.sheet;

import com.github.drsmugleaf.charactersheets.Builder;
import com.github.drsmugleaf.charactersheets.ability.AbilitiesBuilder;
import com.github.drsmugleaf.charactersheets.ability.Ability;
import com.github.drsmugleaf.charactersheets.ability.AbilitySet;
import com.github.drsmugleaf.charactersheets.stat.StatGroup;
import com.github.drsmugleaf.charactersheets.stat.StatsBuilder;
import com.github.drsmugleaf.charactersheets.state.State;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class SheetBuilder implements Builder<Sheet> {

    private final Map<State, StatsBuilder> STAT_BUILDERS = new HashMap<>();
    private final Map<State, AbilitiesBuilder> ABILITY_BUILDERS = new HashMap<>();

    public SheetBuilder() {}

    public ImmutableMap<State, StatsBuilder> getStats() {
        return ImmutableMap.copyOf(STAT_BUILDERS);
    }

    public StatsBuilder getStats(State state) {
        return STAT_BUILDERS.computeIfAbsent(state, (state1) -> new StatsBuilder(state.getName()));
    }

    public SheetBuilder addAbilities(Ability ability) {
        State state = ability.getValidState();
        ABILITY_BUILDERS.computeIfAbsent(state, (state1) -> new AbilitiesBuilder(state1.getName()));
        ABILITY_BUILDERS.get(state).addAbility(ability);
        return this;
    }

    public ImmutableMap<State, AbilitiesBuilder> getAbilities() {
        return ImmutableMap.copyOf(ABILITY_BUILDERS);
    }

    public SheetBuilder setAbilities(Map<State, AbilitiesBuilder> abilities) {
        ABILITY_BUILDERS.clear();
        ABILITY_BUILDERS.putAll(abilities);
        return this;
    }

    @Override
    public Sheet build() {
        Map<State, StatGroup> stats = new HashMap<>();
        for (Map.Entry<State, StatsBuilder> entry : STAT_BUILDERS.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().build());
        }

        Map<State, AbilitySet> abilities = new HashMap<>();
        for (Map.Entry<State, AbilitiesBuilder> entry : ABILITY_BUILDERS.entrySet()) {
            abilities.put(entry.getKey(), entry.getValue().build());
        }

        return new Sheet(stats, abilities);
    }

}
