package com.github.drsmugleaf.pokemon2.generations.iii.pokemon;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature.INature;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public interface IPokemonIII<T extends IPokemonIII<T>> extends IPokemon<T> {

    INature getNature();

}
