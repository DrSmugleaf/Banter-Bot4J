package com.github.drsmugleaf.pokemon2.generations.i.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.stat.stage.IStage;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public enum StagesI implements IStage {

    N6(-6, 0.25),
    N5(-5, 0.28),
    N4(-4, 0.33),
    N3(-3, 0.4),
    N2(-2, 0.5),
    N1(-1, 0.66),
    ZERO(0, 1),
    P1(1, 1.5),
    P2(2, 2),
    P3(3, 2.5),
    P4(4, 3),
    P5(5, 3.5),
    P6(6, 4);

    private final int STEP;
    private final double TEMPORARY_STAT_MULTIPLIER;

    StagesI(int step, double temporaryStatMultiplier) {
        STEP = step;
        TEMPORARY_STAT_MULTIPLIER = temporaryStatMultiplier;
    }

    @Contract(pure = true)
    @Override
    public int getStep() {
        return STEP;
    }

    @Contract(pure = true)
    @Override
    public double getTemporaryStatMultiplier() {
        return TEMPORARY_STAT_MULTIPLIER;
    }

}
