package com.github.drsmugleaf.pokemon.generations.viii.generation;

import com.github.drsmugleaf.pokemon.generations.iii.generation.BaseGenerationIII;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public abstract class BaseGenerationVIII extends BaseGenerationIII implements IGenerationVIII {

    public BaseGenerationVIII() {
        super();
    }

    @Override
    public int getTotalPokemon() {
        return super.getTotalPokemon() - getRemovedPokemon();
    }

}
