package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.tak.board.IPreset;
import org.deeplearning4j.rl4j.space.DiscreteSpace;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class TakSpace extends DiscreteSpace {

    public TakSpace(IPreset preset) {
        super(preset.getAllActions().size());
    }

    @Override
    public Integer noOp() {
        return -1;
    }

}
