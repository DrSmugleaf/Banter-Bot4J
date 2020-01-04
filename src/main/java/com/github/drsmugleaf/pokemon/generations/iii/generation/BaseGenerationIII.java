package com.github.drsmugleaf.pokemon.generations.iii.generation;

import com.github.drsmugleaf.pokemon.generations.ii.generation.BaseGenerationII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.stat.nature.NatureRegistry;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public abstract class BaseGenerationIII extends BaseGenerationII implements IGenerationIII {

    private final NatureRegistry NATURES = new NatureRegistry(this);
    private final AbilityRegistry ABILITIES = new AbilityRegistry(this);

    protected BaseGenerationIII() {
        super();
    }

    @Override
    public NatureRegistry getNatures() {
        return NATURES;
    }

    @Override
    public AbilityRegistry getAbilities() {
        return ABILITIES;
    }

}
