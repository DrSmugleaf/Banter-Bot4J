package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.IBaseStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.stage.IStage;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.stage.StageRegistry;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public abstract class Stat<T extends IPokemon<T>> implements IStat {

    private final IBaseStat<T> STAT;
    private final int IV;
    private final int EV;
    private IStage stage;

    public Stat(IBaseStat<T> stat, int iv, int ev, StageRegistry stages) {
        STAT = stat;
        IV = iv;
        EV = ev;
        stage = stages.get(0);
    }

    @Override
    public IBaseStat<T> getBaseStat() {
        return STAT;
    }

    @Override
    public int getIndividualValue() {
        return IV;
    }

    @Override
    public int getEffortValue() {
        return EV;
    }

    @Override
    public IStage getStage() {
        return stage;
    }

}
