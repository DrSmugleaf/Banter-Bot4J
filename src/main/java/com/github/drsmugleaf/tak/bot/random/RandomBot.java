package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.pieces.Color;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public abstract class RandomBot extends Bot {

    protected RandomBot(String name, IGame game, Color color, Preset preset) {
        super(name, game, color, preset, false);
    }

    @Nullable
    @Override
    public ICoordinates getNextAction() {
        List<ICoordinates> availableActions = getAvailableActions();
        if (availableActions.isEmpty()) {
            return null;
        }

        int random = ThreadLocalRandom.current().nextInt(availableActions.size());
        return availableActions.get(random);
    }

}
