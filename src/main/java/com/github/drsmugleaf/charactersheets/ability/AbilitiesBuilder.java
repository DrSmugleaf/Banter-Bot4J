package com.github.drsmugleaf.charactersheets.ability;

import com.github.drsmugleaf.charactersheets.Builder;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 13/07/2019
 */
public class AbilitiesBuilder implements Builder<AbilitySet> {

    private final String NAME;
    private Map<String, Ability> abilities = new HashMap<>();

    public AbilitiesBuilder(String name) {
        NAME = name;
    }

    public ImmutableMap<String, Ability> getAbilities() {
        return ImmutableMap.copyOf(abilities);
    }

    public AbilitiesBuilder addAbility(Ability ability) {
        abilities.put(ability.getName(), ability);
        return this;
    }

    public AbilitiesBuilder setAbilities(Map<String, Ability> abilities) {
        this.abilities = abilities;
        return this;
    }

    @Override
    public AbilitySet build() {
        return new AbilitySet(NAME, abilities);
    }

}
