package com.github.drsmugleaf.pokemon.base.battle;

import com.github.drsmugleaf.pokemon.base.battle.field.IField;
import com.github.drsmugleaf.pokemon.base.battle.turn.ITurn;
import com.github.drsmugleaf.pokemon.base.battle.turn.snapshot.ISnapshot;
import com.github.drsmugleaf.pokemon.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.google.common.collect.ImmutableList;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IBattle<T extends IBattlePokemon<T>> {

    IGeneration getGeneration();
    IField<T> getField();
    ImmutableList<ITurn<T>> getTurns();
    ITurn<T> getTurn();
    ITurn<T> getTurn(int i);
    ITurn<T> getPreviousTurn();
    boolean bugsEnabled();
    ISnapshot<T> snapshot();

}
