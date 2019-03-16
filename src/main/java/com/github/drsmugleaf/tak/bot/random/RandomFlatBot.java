package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class RandomFlatBot extends RandomBot {

    protected RandomFlatBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new RandomFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @NotNull
    @Override
    public List<Coordinates> getAvailableActions(@NotNull Board board) {
        return getAvailablePlaces(board, Type.FLAT_STONE);
    }

}
