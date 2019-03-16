package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.MovingCoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by DrSmugleaf on 05/01/2019
 */
public class RandomFlatMoveBot extends RandomFlatBot {

    protected RandomFlatMoveBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new RandomFlatMoveBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @NotNull
    @Override
    public List<Coordinates> getAvailableActions(@NotNull Board board) {
        List<MovingCoordinates> moves = getAvailableMoves(board);
        List<Coordinates> places = getAvailablePlaces(board);
        places.addAll(moves);

        return places;
    }

}
