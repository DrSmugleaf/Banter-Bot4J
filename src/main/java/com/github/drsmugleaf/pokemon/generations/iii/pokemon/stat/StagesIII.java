package com.github.drsmugleaf.pokemon.generations.iii.pokemon.stat;

import com.github.drsmugleaf.pokemon.base.pokemon.stat.stage.IStage;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public enum StagesIII implements IStage {

    N6(-6, 0.33),
    N5(-5, 0.36),
    N4(-4, 0.43),
    N3(-3, 0.5),
    N2(-2, 0.6),
    N1(-1, 0.75),
    ZERO(0, 1),
    P1(1, 1.33),
    P2(2, 1.66),
    P3(3, 2),
    P4(4, 2.5),
    P5(5, 2.66),
    P6(6, 3);

    private final int STEP;
    private final double TEMPORARY_STAT_MULTIPLIER;

    StagesIII(int step, double temporaryStatMultiplier) {
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
