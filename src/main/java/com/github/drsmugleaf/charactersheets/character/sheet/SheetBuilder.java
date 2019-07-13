package com.github.drsmugleaf.charactersheets.character.sheet;

import com.github.drsmugleaf.charactersheets.Builder;
import com.github.drsmugleaf.charactersheets.ability.Abilities;
import com.github.drsmugleaf.charactersheets.stat.Stat;
import com.github.drsmugleaf.charactersheets.stat.StatGroup;
import com.github.drsmugleaf.charactersheets.stat.StatsBuilder;
import com.github.drsmugleaf.charactersheets.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class SheetBuilder implements Builder<Sheet> {

    private Map<State, StatGroup> stats = new HashMap<>();
    private Map<State, Abilities> abilities = new TreeMap<>();

    public SheetBuilder() {}

    public SheetBuilder(Sheet sheet) {
        stats.putAll(sheet.getStats());
        abilities.putAll(sheet.getAbilities());
    }

    public Map<State, StatGroup> getStats() {
        return stats;
    }

    public SheetBuilder addStats(State state, StatGroup statGroup) {
        this.stats.computeIfAbsent(state, (state1) -> new StatsBuilder().setName(state1.getName()).build());
        StatGroup originalStatGroup = this.stats.get(state);
        StatsBuilder builder = new StatsBuilder(originalStatGroup);

        for (Stat stat : statGroup.get().values()) {
            builder.addStat(stat);
        }

        this.stats.put(state, builder.build());

        return this;
    }

    public SheetBuilder setStats(Map<State, StatGroup> stats) {
        this.stats = stats;
        return this;
    }

    public SheetBuilder addAbilities(Map.Entry<State, StatGroup> entry) {
        this.stats.put(entry.getKey(), entry.getValue());
        return this;
    }

    public Map<State, Abilities> getAbilities() {
        return abilities;
    }

    public SheetBuilder setAbilities(Map<State, Abilities> abilities) {
        this.abilities = abilities;
        return this;
    }

    @Override
    public Sheet build() {
        return new Sheet(stats, abilities);
    }

}
