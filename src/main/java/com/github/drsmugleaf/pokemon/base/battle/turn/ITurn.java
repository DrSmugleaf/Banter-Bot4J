package com.github.drsmugleaf.pokemon.base.battle.turn;

import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon.base.pokemon.move.IMoveReport;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface ITurn<T extends IBattlePokemon<T>> {

    ImmutableSet<IMoveReport<T>> getMoves();
    IMoveReport<T> getMove();
    IMoveReport<T> getPreviousMove();
    void addMove(IMoveReport<T> move);

}
