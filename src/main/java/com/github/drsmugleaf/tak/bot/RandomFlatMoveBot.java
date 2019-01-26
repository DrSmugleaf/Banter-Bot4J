package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.MovingCoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
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
        Type type;
        if (getHand().has(Type.FLAT_STONE)) {
            type = Type.FLAT_STONE;
        } else if (getHand().has(Type.CAPSTONE)) {
            type = Type.CAPSTONE;
        } else {
            return null;
        }

        boolean move = ThreadLocalRandom.current().nextBoolean();
        if (move && getHand().getPieces() < getGame().getBoard().getPreset().getStones()) {
            List<MovingCoordinates> availableMoves = getAvailableMoves(type);
            if (availableMoves.isEmpty()) {
                return null;
            }

            int random = ThreadLocalRandom.current().nextInt(availableMoves.size());
            return availableMoves.get(random);
        } else {
            return super.getNextMove();
        }
    }

}
