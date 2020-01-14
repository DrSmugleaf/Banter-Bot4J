package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import com.github.drsmugleaf.tak.board.layout.IPreset;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class GameResults {

    private final FeaturesFlat FEATURES;
    private final Labels LABELS;
    private final int ITERATIONS;
    private final int MAX_TURNS;
    private final IPreset PRESET;

    public GameResults(FeaturesFlat features, Labels labels, int iterations, int maxTurns, IPreset preset) {
        FEATURES = features;
        LABELS = labels;
        ITERATIONS = iterations;
        MAX_TURNS = maxTurns;
        PRESET = preset;
    }

    public FeaturesFlat getFeatures() {
        return FEATURES;
    }

    public Labels getLabels() {
        return LABELS;
    }

    public int getIterations() {
        return ITERATIONS;
    }

    public int getMaxTurns() {
        return MAX_TURNS;
    }

    public IPreset getPreset() {
        return PRESET;
    }

}
