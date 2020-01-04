package com.github.drsmugleaf.pokemon.generations.v.pokemon.stat;

import com.github.drsmugleaf.pokemon.base.pokemon.stat.stage.IStage;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public enum StagesV implements IStage {

    N6(-6),
    N5(-5),
    N4(-4),
    N3(-3),
    N2(-2),
    N1(-1),
    ZERO(0),
    P1(1),
    P2(2),
    P3(3),
    P4(4),
    P5(5),
    P6(6);

    private final int STEP;

    StagesV(int step) {
        STEP = step;
    }

    @Contract(pure = true)
    @Override
    public int getStep() {
        return STEP;
    }

    @Override
    public double getTemporaryStatMultiplier() {
        double dividend = 3;
        double divisor = 3;

        int step = getStep();
        if (step > 0) {
            dividend += step;
        } else if (step < 0) {
            divisor += step;
        }

        return dividend / divisor;
    }

}
