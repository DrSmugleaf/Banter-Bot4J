package com.github.drsmugleaf.pokemon2.base.battle.field;

import com.github.drsmugleaf.pokemon2.base.battle.side.ISide;
import com.github.drsmugleaf.pokemon2.base.battle.variant.IBattleVariant;
import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IField<T extends IBattlePokemon> {

    ImmutableSet<ISide<T>> getSides();
    IBattleVariant getVariant();
    T getPokemon(T pokemon);

}
