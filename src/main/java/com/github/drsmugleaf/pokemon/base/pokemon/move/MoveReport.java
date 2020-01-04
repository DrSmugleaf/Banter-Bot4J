package com.github.drsmugleaf.pokemon.base.pokemon.move;

import com.github.drsmugleaf.pokemon.base.battle.turn.snapshot.ISnapshot;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 29/11/2019
 */
public class MoveReport<T extends IBattlePokemon<T>> implements IMoveReport<T> {

    private final T USER;
    private final T TARGETED;
    private final ImmutableSet<T> TARGETS;
    private final IMoveInformation<T> MOVE;
    private final boolean HIT;
    private final int DAMAGE;
    private final ISnapshot<T> BEFORE_SNAPSHOT;

    public MoveReport(IMoveInformation<T> move, T user, T targeted, boolean hit, int damage) {
        USER = user;
        TARGETED = targeted;
        TARGETS = ImmutableSet.of(); // TODO: 29-Nov-19 Targeting
        MOVE = move;
        HIT = hit;
        DAMAGE = damage;
        BEFORE_SNAPSHOT = user.getBattle().snapshot();
    }

    @Override
    public T getUser() {
        return USER;
    }

    @Override
    public T getTargeted() {
        return TARGETED;
    }

    @Override
    public ImmutableSet<T> getTargets() {
        return TARGETS;
    }

    @Override
    public IMoveInformation<T> getMove() {
        return MOVE;
    }

    @Override
    public boolean hit() {
        return HIT;
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }

    @Override
    public ISnapshot<T> getBeforeSnapshot() {
        return BEFORE_SNAPSHOT;
    }

}
