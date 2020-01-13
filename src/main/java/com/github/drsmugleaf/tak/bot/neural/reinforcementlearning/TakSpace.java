package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.tak.board.IAction;
import com.github.drsmugleaf.tak.game.IGame;
import org.deeplearning4j.rl4j.space.DiscreteSpace;

import java.util.List;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class TakSpace extends DiscreteSpace {

    private final IGame GAME;

    public TakSpace(IGame game) {
        super(game.getBoard().getPreset().getAllActions().size());
        GAME = game;
    }

    @Override
    public Integer randomAction() {
        List<IAction> actions = GAME.getNextPlayer().getAvailableActions();
        if (actions.isEmpty()) {
            return noOp();
        }

        int randomIndex = rnd.nextInt(actions.size());
        IAction randomAction = actions.get(randomIndex);
        System.out.println("Space: " + actions.size());
        return GAME.getBoard().getPreset().getAllActions().indexOf(randomAction);
    }

    @Override
    public Integer noOp() {
        return -1;
    }

}
