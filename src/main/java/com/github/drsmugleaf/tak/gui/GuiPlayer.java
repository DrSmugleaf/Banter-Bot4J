package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 31/12/2018
 */
public class GuiPlayer extends Player {

    @NotNull
    private final GuiGame GAME;

    public GuiPlayer(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);

        if (!(game instanceof GuiGame)) {
            throw new IllegalArgumentException("Can't add " + GuiPlayer.class + " to game instance that isn't " + GuiGame.class);
        }
        GAME = (GuiGame) game;

        SquareButton[][] pieces = GAME.BOARD_PANEL.pieces;
        for (int rowIndex = 0; rowIndex < pieces.length; rowIndex++) {
            SquareButton[] row = pieces[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                SquareButton square = row[columnIndex];
                int finalRowIndex = rowIndex;
                int finalColumnIndex = columnIndex;
                square.getButton().addActionListener(e -> {
                    if (game.getNextPlayer() == this) {
                        place(Type.FLAT_STONE, finalRowIndex, finalColumnIndex);
                        game.getNextPlayer().nextTurn();
                    }
                });
            }
        }
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new GuiPlayer(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public void nextTurn() {}

}
