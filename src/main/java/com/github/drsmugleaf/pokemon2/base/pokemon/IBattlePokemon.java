package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IModifiers;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveReport;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface IBattlePokemon<T extends IBattlePokemon<T>> extends IPokemon<T> {

    int getID();
    IBattle<T> getBattle();
    @Nullable INonVolatileStatus<T> getStatus();
    void setStatus(@Nullable INonVolatileStatus<T> status);
    IPokemonState getState();
    void setState(IPokemonState state);
    IModifiers getModifiers();
    void targeted(IMoveReport<T> report);
    ImmutableMap<IBattlePokemon<T>, IMoveReport<T>> getLastTargetedBy();
    @Nullable default IMoveReport<T> getLastMoveTargetedBy() {
        var iterator = getLastTargetedBy().values().iterator();
        IMoveReport<T> last = null;
        while (iterator.hasNext()) {
            last = iterator.next();
        }

        return last;
    }
    void removeLastTargetedBy(IBattlePokemon<T> pokemon);
    boolean isActive();
    void setActive();
    void setInactive();
    @Nullable default T getRandomTarget() {
        return getBattle().getField().getRandomTarget(this);
    }
    default boolean hasValidTarget() {
        return getBattle().getField().hasValidTarget(this);
    }

}
