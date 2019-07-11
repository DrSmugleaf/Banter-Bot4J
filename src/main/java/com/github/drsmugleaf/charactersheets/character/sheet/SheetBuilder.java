package com.github.drsmugleaf.charactersheets.character.sheet;

import com.github.drsmugleaf.charactersheets.Builder;
import com.github.drsmugleaf.charactersheets.ability.Abilities;
import com.github.drsmugleaf.charactersheets.stat.Stats;
import com.github.drsmugleaf.charactersheets.state.State;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class SheetBuilder implements Builder<Sheet> {

    private Map<State, Stats> stats = new TreeMap<>();
    private Map<State, Abilities> abilities = new TreeMap<>();

    public SheetBuilder() {}

    public SheetBuilder(Sheet sheet) {
        stats.putAll(sheet.getStats());
        abilities.putAll(sheet.getAbilities());
    }

    public Map<State, Stats> getStats() {
        return stats;
    }

    public SheetBuilder addStats(State state, Stats stats) {
        this.stats.put(state, stats);
        return this;
    }

    public SheetBuilder setStats(Map<State, Stats> stats) {
        this.stats = stats;
        return this;
    }

    public SheetBuilder addAbilities(Map.Entry<State, Stats> entry) {
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
