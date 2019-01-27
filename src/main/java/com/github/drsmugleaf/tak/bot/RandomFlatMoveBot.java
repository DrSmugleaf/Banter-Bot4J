package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.MovingCoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

    @Nullable
    @Override
    public Coordinates getNextMove() {
        boolean move = ThreadLocalRandom.current().nextBoolean();
        List<MovingCoordinates> availableMoves = getAvailableMoves();
        if (!move || availableMoves.isEmpty()) {
            return super.getNextMove();
        }

        int random = ThreadLocalRandom.current().nextInt(availableMoves.size());
        return availableMoves.get(random);
    }

}
