package com.github.drsmugleaf.pokemon2.generations.iii.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.ability.IAbility;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.ISpeciesIII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature.INature;

import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public abstract class PokemonIII<T extends PokemonIII<T, S>, S extends ISpeciesIII> extends Pokemon<T, S> implements IPokemonIII<T> {

    private final INature NATURE;
    private final IAbility ABILITY;

    public PokemonIII(
            S species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<T>> stats,
            int hp,
            int maxHp,
            INature nature,
            IAbility ability
    ) {
        super(species, nickname, types, item, gender, level, stats, hp, maxHp);
        NATURE = nature;
        ABILITY = ability;
    }

    @Override
    public INature getNature() {
        return NATURE;
    }

    @Override
    public IAbility getAbility() {
        return ABILITY;
    }

}
