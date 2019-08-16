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

    private Map<State, StatsBuilder> statBuilders = new HashMap<>();
    private Map<State, AbilitiesBuilder> abilityBuilders = new HashMap<>();

    public SheetBuilder() {}

    public ImmutableMap<State, StatsBuilder> getStats() {
        return ImmutableMap.copyOf(statBuilders);
    }

    public StatsBuilder getStats(State state) {
        return statBuilders.computeIfAbsent(state, (state1) -> new StatsBuilder(state.getName()));
    }

    public SheetBuilder addAbilities(Ability ability) {
        State state = ability.getValidState();
        abilityBuilders.computeIfAbsent(state, (state1) -> new AbilitiesBuilder(state1.getName()));
        abilityBuilders.get(state).addAbility(ability);
        return this;
    }

    public ImmutableMap<State, AbilitiesBuilder> getAbilities() {
        return ImmutableMap.copyOf(abilityBuilders);
    }

    public SheetBuilder setAbilities(Map<State, AbilitiesBuilder> abilities) {
        this.abilityBuilders = abilities;
        return this;
    }

    @Override
    public Sheet build() {
        Map<State, StatGroup> stats = new HashMap<>();
        for (Map.Entry<State, StatsBuilder> entry : statBuilders.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().build());
        }

        Map<State, AbilitySet> abilities = new HashMap<>();
        for (Map.Entry<State, AbilitiesBuilder> entry : abilityBuilders.entrySet()) {
            abilities.put(entry.getKey(), entry.getValue().build());
        }

        return new Sheet(stats, abilities);
    }

}
