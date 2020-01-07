package com.github.drsmugleaf.pokemon.base.pokemon.stat.stage;

import com.github.drsmugleaf.pokemon.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public interface IStage extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns.put("name", getName());

        return columns.get();
    }

    int getStep();
    default double getPermanentStatMultiplier() {
        double dividend = 2;
        double divisor = 2;

        int step = getStep();
        if (step > 0) {
            dividend += step;
        } else if (step < 0) {
            divisor += step;
        }

        return dividend / divisor;
    }
    double getTemporaryStatMultiplier();
    default double getStatMultiplier(IStat<?> stat) {
        return stat.isPermanent() ? getPermanentStatMultiplier() : getTemporaryStatMultiplier();
    }
    @Override
    default String getName() {
        return Integer.toString(getStep());
    }

}
