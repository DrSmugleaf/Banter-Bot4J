package com.github.drsmugleaf.pokemon.base.pokemon.move.effect;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon.base.pokemon.move.IMoveReport;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IEffect<T extends IBattlePokemon<T>> extends Nameable {

    ImmutableSet<Integer> EMPTY = ImmutableSet.of();

    int getID();
    IMoveReport<T> target(IMoveInformation<T> move, T user, T target);
    int damage(IMoveInformation<T> move, T user, T target);
    boolean hits(IMoveInformation<T> move, T user, T target);
    void effect(IMoveInformation<T> move, T user, T target, IMoveReport<T> report);
    boolean doesEffect(IMoveInformation<T> move, T user, T target, IMoveReport<T> report);
    int getDamage(IMoveInformation<T> move, T user, T target);
    default ImmutableSet<Integer> getIgnoredEffects() {
        return EMPTY;
    }
    default ImmutableSet<Integer> getIncompatibleMoves() {
        return EMPTY;
    }
    default ImmutableSet<Integer> getCallMoves() {
        return EMPTY;
    }

}
