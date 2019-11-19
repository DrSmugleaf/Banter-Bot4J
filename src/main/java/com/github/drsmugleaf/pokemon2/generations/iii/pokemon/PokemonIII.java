package com.github.drsmugleaf.pokemon2.generations.iii.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.ISpeciesIII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature.INature;

import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public abstract class PokemonIII<T extends ISpeciesIII<T>> extends Pokemon<T> implements IPokemonIII<T> {

    private final T SPECIES;
    private final INature NATURE;

    public PokemonIII(
            T species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<IPokemon<T>>> stats,
            int hp,
            INature nature
    ) {
        super(species, nickname, types, item, gender, level, stats, hp);
        SPECIES = species;
        NATURE = nature;
    }

    public T getSpecies() {
        return SPECIES;
    }

    @Override
    public INature getNature() {
        return NATURE;
    }

}
