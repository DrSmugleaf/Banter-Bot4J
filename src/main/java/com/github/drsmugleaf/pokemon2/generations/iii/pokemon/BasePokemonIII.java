package com.github.drsmugleaf.pokemon2.generations.iii.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.BasePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature.INature;

import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public abstract class BasePokemonIII<T extends IPokemonIII<T>> extends BasePokemon<T> implements IPokemonIII<T> {

    private final INature NATURE;

    public BasePokemonIII(
            T species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<T>> stats,
            int hp,
            INature nature
    ) {
        super(species, nickname, types, item, gender, level, stats, hp);
        NATURE = nature;
    }

    @Override
    public INature getNature() {
        return NATURE;
    }

}
