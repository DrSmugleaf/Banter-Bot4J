package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import org.deeplearning4j.rl4j.space.DiscreteSpace;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class TakSpace extends DiscreteSpace {

    private final Game GAME;

    public TakSpace(Game game, Preset preset) {
        super(preset.getAllActions().size());
        GAME = game;
    }

    @Override
    public int getSize() {
        return GAME.getNextPlayer().getAvailableActions().size();
    }

    @Override
    public Integer randomAction() {
        int actions = GAME.getNextPlayer().getAvailableActions().size();
        if (actions <= 0) {
            return noOp();
        }

        return rd.nextInt(actions);
    }

    @Override
    public Integer noOp() {
        return -1;
    }

}
