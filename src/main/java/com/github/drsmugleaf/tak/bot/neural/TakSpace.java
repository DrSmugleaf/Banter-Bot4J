package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.tak.board.Preset;
import org.deeplearning4j.rl4j.space.DiscreteSpace;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class TakSpace extends DiscreteSpace {

    private final INeuralGame GAME;

    public TakSpace(INeuralGame game, Preset preset) {
        super(preset.getAllActions().size());
        GAME = game;
    }

    @Override
    public Integer randomAction() {
        int actions = GAME.getNextPlayer().getAvailableActions().size();
        if (actions <= 0) {
            return noOp();
        }

        return rnd .nextInt(actions);
    }

    @Override
    public Integer noOp() {
        return -1;
    }

}
