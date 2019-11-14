package com.github.drsmugleaf.pokemon2.base.battle.variant;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IBattleVariant extends Nameable {

    int getMinTrainers();
    int getMaxTrainers();
    int getMinActivePokemon();
    int getMaxActivePokemon();
    int getMinPartySize();
    int getMaxPartySize();

}
