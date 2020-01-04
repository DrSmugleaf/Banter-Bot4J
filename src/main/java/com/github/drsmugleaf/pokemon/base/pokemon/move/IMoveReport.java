package com.github.drsmugleaf.pokemon.base.pokemon.move;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.battle.turn.snapshot.ISnapshot;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IMoveReport<T extends IBattlePokemon<T>> {

    T getUser();
    @Nullable T getTargeted();
    ImmutableSet<T> getTargets();
    IMoveInformation<T> getMove();
    boolean hit();
    int getDamage();
    ISnapshot<T> getBeforeSnapshot();

}
