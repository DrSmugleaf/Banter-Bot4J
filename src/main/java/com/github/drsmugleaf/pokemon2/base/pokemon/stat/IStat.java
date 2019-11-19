package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.IBaseStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.stage.IStage;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IStat<T extends IPokemon> extends IBaseStat<T>, Nameable {

    IBaseStat<T> getBaseStat();
    int getIndividualValue();
    int getEffortValue();
    IStage getStage();

}
