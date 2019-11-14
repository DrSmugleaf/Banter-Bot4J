package com.github.drsmugleaf.pokemon2.base.battle.variant;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IBattleVariant extends Nameable {

    default int getMinTrainers() {
        return getMaxTrainers();
    }
    int getMaxTrainers();
    default int getMinActivePokemon() {
        return getMaxActivePokemon();
    }
    int getMaxActivePokemon();
    default int getMinPartySize() {
        return getMaxPartySize();
    }
    int getMaxPartySize();

}
