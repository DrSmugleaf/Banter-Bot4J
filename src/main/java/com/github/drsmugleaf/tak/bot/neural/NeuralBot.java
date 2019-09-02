package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 16/08/2019
 */
public class NeuralBot extends Bot {

    public static int win = 0;
    public static int loss = 0;
    public static int tie = 0;

    protected NeuralBot(String name, Game game, Color color, Preset preset) {
        super(name, game, color, preset, true);
    }

    @Contract("_ -> new")
    public static Player from(PlayerInformation information) {
        return new NeuralBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Nullable
    @Override
    public ICoordinates getNextAction() {
//        String directory = Keys.TAK_POLICY_DIRECTORY.VALUE;
//        DQNPolicy<NeuralBoard> policy;
//        try {
//            policy = DQNPolicy.load(directory + "/pol1");
//        } catch (IOException e) {
//            throw new UncheckedIOException("Error loading policy", e);
//        }

        return null;
    }

    @Override
    public void onGameEnd(@Nullable Player winner) {
        if (winner == null) tie++;
        if (winner == this) win++; else loss++;
    }
}
