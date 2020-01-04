package com.github.drsmugleaf.pokemon.base.battle.field;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.battle.side.ISide;
import com.github.drsmugleaf.pokemon.base.battle.variant.IBattleVariant;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.google.common.collect.ImmutableSet;

import java.util.List;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IField<T extends IBattlePokemon<T>> {

    ImmutableSet<ISide<T>> getSides();
    IBattleVariant getVariant();
    T getPokemon(T pokemon);
    List<T> getTargets(T pokemon);
    @Nullable T getRandomTarget(IBattlePokemon<T> pokemon);
    boolean hasValidTarget(IBattlePokemon<T> pokemon);

}
