package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.tak.game.IGame;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface INeuralGame extends IGame {

    @Override
    INeuralBoard getBoard();

}
