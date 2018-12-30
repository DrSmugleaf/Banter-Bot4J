package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class RandomFlatBot extends Player {

    public RandomFlatBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new RandomFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public void nextTurn() {
        if (getHand().has(Type.FLAT_STONE)) {
            getCoordinates(Type.FLAT_STONE).place(this, Type.FLAT_STONE);
        } else if (getHand().has(Type.CAPSTONE)) {
            getCoordinates(Type.CAPSTONE).place(this, Type.CAPSTONE);
        } else {
            surrender();
        }
    }

    @NotNull
    private Coordinates getCoordinates(@NotNull Type type) {
        int boardSize = getGame().getBoard().getPreset().getSize();
        int randomRow = ThreadLocalRandom.current().nextInt(0, boardSize + 1);
        int randomColumn = ThreadLocalRandom.current().nextInt(0 , boardSize + 1);
        int attempts = 0;

        while (!canPlace(type, randomRow, randomColumn) && attempts < 100) {
            randomRow = ThreadLocalRandom.current().nextInt(0, boardSize + 1);
            randomColumn = ThreadLocalRandom.current().nextInt(0, boardSize + 1);
            attempts++;
        }

        if (attempts >= 100) {
            surrender();
        }

        return new Coordinates(randomRow, randomColumn);
    }

}
