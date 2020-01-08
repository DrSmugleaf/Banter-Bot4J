package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public class NeuralGame extends Game implements INeuralGame {

    public NeuralGame(
            INeuralBoard board,
            String playerName1,
            String playerName2,
            Function<PlayerInformation, Player> playerMaker1,
            Function<PlayerInformation, Player> playerMaker2
    ) {
        super(board, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    @Override
    public INeuralBoard getBoard() {
        return (INeuralBoard) super.getBoard();
    }

}
