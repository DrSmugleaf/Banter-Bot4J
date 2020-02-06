package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import org.jetbrains.annotations.Contract;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.List;

/**
 * Created by DrSmugleaf on 16/08/2019
 */
public class NeuralBot extends Bot {

    public static int win = 0;
    public static int loss = 0;
    public static int tie = 0;

    protected NeuralBot(String name, Game game, IColor color) {
        super(name, game, color, false);
    }

    @Contract("_ -> new")
    public static IPlayer from(IPlayerInformation information) {
        return new NeuralBot(information.getName(), information.getGame(), information.getColor());
    }

    @Nullable
    @Override
    public IAction getNextAction() {
        NeuralBoard board = (NeuralBoard) getGame().getBoard();
        INDArray array = Nd4j.create(board.toArray());
        Integer action = TakMDP.getPolicy().nextAction(array);
        List<IAction> actions = getAvailableActions();
        return actions.get(action);
    }

    @Override
    public void onGameEnd(@Nullable IPlayer winner) {
        if (winner == null) tie++;
        if (winner == this) win++; else loss++;
    }
}
