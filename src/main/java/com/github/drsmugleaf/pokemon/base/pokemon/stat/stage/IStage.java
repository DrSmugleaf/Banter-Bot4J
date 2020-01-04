package com.github.drsmugleaf.pokemon.base.pokemon.stat.stage;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.IStat;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public interface IStage extends Nameable {

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
    default double getStatMultiplier(IStat stat) {
        return stat.isPermanent() ? getPermanentStatMultiplier() : getTemporaryStatMultiplier();
    }
    @Override
    default String getName() {
        return Integer.toString(getStep());
    }

}
