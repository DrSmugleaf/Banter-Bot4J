package com.github.drsmugleaf.charactersheets.character;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.charactersheets.stat.Stat;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class CharacterBuilder implements Builder<Character> {

    @Nullable
    private String name;
    private Map<String, Stat> stats = new TreeMap<>();

    public CharacterBuilder() {}

    @Nullable
    public String getName() {
        return name;
    }

    public CharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Nullable
    public Map<String, Stat> getStats() {
        return stats;
    }

    public CharacterBuilder addStats(Stat stat) {
        this.stats.put(stat.getName(), stat);
        return this;
    }

    public CharacterBuilder setStats(Map<String, Stat> stats) {
        this.stats = stats;
        return this;
    }

    @Override
    public Character build() {
        return new Character(name);
    }

}
