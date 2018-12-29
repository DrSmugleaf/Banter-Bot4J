package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.bot.RandomFlatBot;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class MinimaxFlatBot extends Player {

    public MinimaxFlatBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        if (information.NAME.equals("2")) {
            return RandomFlatBot.from(information);
        }

        return new MinimaxFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public void nextTurn() {
        if (!getHand().has(Type.FLAT_STONE)) {
            surrender();
        }

        Node node = new Node(getGame().getBoard().copy(), getHand().getColor());
        node.computeToDepth(2, getHand().getColor());
        Node bestNode = node.getBestChild();

        if (bestNode != null) {
            Coordinates coordinates = bestNode.getCoordinates();
            if (coordinates != null) {
                coordinates.place(this, Type.FLAT_STONE);
                return;
            }
        }

        surrender();
    }

}
