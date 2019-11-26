package com.github.drsmugleaf.pokemon2.base.pokemon.move;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.turn.snapshot.ISnapshot;
import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
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
