package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class RandomFlatBot extends Bot {

    private RandomFlatBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new RandomFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
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

        int boardSize = getGame().getBoard().getPreset().getSize();
        int randomRow = ThreadLocalRandom.current().nextInt(0, boardSize + 1);
        int randomColumn = ThreadLocalRandom.current().nextInt(0 , boardSize + 1);
        int attempts = 0;

        while (!canPlace(type, randomColumn, randomRow)) {
            if (attempts >= 100) {
                return null;
            }

            randomRow = ThreadLocalRandom.current().nextInt(0, boardSize + 1);
            randomColumn = ThreadLocalRandom.current().nextInt(0, boardSize + 1);
            attempts++;
        }

        return new Coordinates(randomColumn, randomRow, type);
    }

}
