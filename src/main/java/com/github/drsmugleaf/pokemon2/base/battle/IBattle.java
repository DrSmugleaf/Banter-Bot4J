package com.github.drsmugleaf.pokemon2.base.battle;

import com.github.drsmugleaf.pokemon2.base.battle.side.ISide;
import com.github.drsmugleaf.pokemon2.base.battle.variant.IBattleVariant;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;

import java.util.Set;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IBattle<T extends IPokemon<T>> {

    IGeneration getGeneration();
    Set<ISide<T>> getTrainers();
    IBattleVariant getVariant();
    boolean bugsEnabled();

}
