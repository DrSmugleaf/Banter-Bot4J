package com.github.drsmugleaf.pokemon.base.pokemon.stat;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.base.IBaseStat;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.stage.IStage;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IStat<T extends IPokemon<T>> extends IBaseStat<T>, Nameable {

    IBaseStat<T> getBaseStat();
    int getIndividualValue();
    int getEffortValue();
    IStage getStage();

}
