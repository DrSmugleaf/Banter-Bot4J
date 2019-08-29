package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 21/08/2019
 */
public class NeuralGame extends Game {

    public NeuralGame(NeuralBoard board, String playerName1, String playerName2, Function<PlayerInformation, Player> playerMaker1, Function<PlayerInformation, Player> playerMaker2) {
        super(board, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    public NeuralGame(Preset preset, String playerName1, String playerName2, Function<PlayerInformation, Player> playerMaker1, Function<PlayerInformation, Player> playerMaker2) {
        super(new NeuralBoard(preset), playerName1, playerName2, playerMaker1, playerMaker2);
    }

    @Override
    public NeuralBoard getBoard() {
        return (NeuralBoard) super.getBoard();
    }

}
