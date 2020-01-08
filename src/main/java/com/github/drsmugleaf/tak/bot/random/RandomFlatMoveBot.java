package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import java.util.List;

/**
 * Created by DrSmugleaf on 05/01/2019
 */
public class RandomFlatMoveBot extends RandomBot {

    protected RandomFlatMoveBot(String name, Game game, Color color, Preset preset) {
        super(name, game, color, preset);
    }

    public static Player from(PlayerInformation information) {
        return new RandomFlatMoveBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public List<ICoordinates> getAvailableActions(IBoard board) {
        return getAvailableActions(board, Type.FLAT_STONE);
    }

}