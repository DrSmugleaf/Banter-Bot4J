package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.IBaseStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.stage.IStage;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IStat extends IBaseStat {

    IBaseStat getBaseStat();
    int getIndividualValue();
    int getEffortValue();
    IStage getStage();

    @Override
    default String getAbbreviation() {
        return getBaseStat().getAbbreviation();
    }

    @Override
    default boolean isPermanent() {
        return getBaseStat().isPermanent();
    }

    @Override
    default int calculate(IPokemon<?> pokemon) {
        return getBaseStat().calculate(pokemon);
    }

    @Override
    default String getName() {
        return getBaseStat().getName();
    }

}
