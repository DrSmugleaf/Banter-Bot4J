package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import org.deeplearning4j.rl4j.space.DiscreteSpace;

import java.util.List;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class TakSpace extends DiscreteSpace {

    private final Game GAME;

    public TakSpace(Game game, Preset preset) {
        super(getSize(preset));
        GAME = game;
    }

    public static int getSize(Preset preset) {
        return preset.getSize() * preset.getSize() * (preset.getCapstones() + preset.getStones());
    }

    @Override
    public Integer randomAction() {
        List<ICoordinates> actions = GAME.getNextPlayer().getAvailableActions();
        if (actions.isEmpty()) {
            return noOp();
        }

        return rd.nextInt(actions.size());
    }

    @Override
    public int getSize() {
        return GAME.getNextPlayer().getAvailableActions().size();
    }

    @Override
    public Integer noOp() {
        return -1;
    }

}
