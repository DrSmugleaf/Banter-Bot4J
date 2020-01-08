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
    private final Map<String, Ability> ABILITIES = new HashMap<>();

    public AbilitiesBuilder(String name) {
        NAME = name;
    }

    public ImmutableMap<String, Ability> getAbilities() {
        return ImmutableMap.copyOf(ABILITIES);
    }

    public AbilitiesBuilder addAbility(Ability ability) {
        ABILITIES.put(ability.getName(), ability);
        return this;
    }

    public AbilitiesBuilder setAbilities(Map<String, Ability> abilities) {
        ABILITIES.clear();
        ABILITIES.putAll(abilities);
        return this;
    }

    @Override
    public AbilitySet build() {
        return new AbilitySet(NAME, ABILITIES);
    }

}
