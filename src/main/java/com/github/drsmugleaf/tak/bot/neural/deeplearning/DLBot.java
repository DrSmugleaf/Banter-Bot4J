package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class DLBot extends Bot {

    private final MultiLayerNetwork NETWORK;

    protected DLBot(String name, IGame game, IColor color, MultiLayerNetwork network) {
        super(name, game, color, false);
        NETWORK = network;
    }

    public static IPlayer from(IPlayerInformation information, MultiLayerNetwork network) {
        return new DLBot(information.getName(), information.getGame(), information.getColor(), network);
    }

    public static Function<IPlayerInformation, IPlayer> from(MultiLayerNetwork network) {
        return information -> from(information, network);
    }

    public MultiLayerNetwork getNetwork() {
        return NETWORK;
    }

    @Override
    public IAction getNextAction() {
        return null;
    }

}
