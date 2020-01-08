package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import java.util.List;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class RandomFlatBot extends RandomBot {

    protected RandomFlatBot(String name, IGame game, Color color, Preset preset) {
        super(name, game, color, preset);
    }

    public static Player from(PlayerInformation information) {
        return new RandomFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public List<ICoordinates> getAvailableActions(IBoard board) {
        return getAvailablePlaces(board, Type.FLAT_STONE);
    }

}
