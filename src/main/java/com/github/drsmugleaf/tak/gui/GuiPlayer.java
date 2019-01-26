package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by DrSmugleaf on 31/12/2018
 */
public class GuiPlayer extends Player {

    @NotNull
    private final GuiGame GAME;

    @NotNull
    private Coordinates NEXT_MOVE;

    public GuiPlayer(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);

        if (!(game instanceof GuiGame)) {
            throw new IllegalArgumentException("Can't add " + GuiPlayer.class + " to game instance that isn't " + GuiGame.class);
        }
        GAME = (GuiGame) game;

        SquareButton[][] pieces = GAME.BOARD_PANEL.pieces;
        for (SquareButton[] row : pieces) {
            for (SquareButton square : row) {
                JButton button = square.getButton();
                button.addMouseListener(new GuiMouseListener(this, square));
            }
        }
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new GuiPlayer(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public void nextTurn() {
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            surrender();
            return;
        }

        NEXT_MOVE.place(this);
    }

    protected void setNextMove(@NotNull Coordinates nextMove) {
        NEXT_MOVE = nextMove;

        synchronized (this) {
            notify();
        }
    }

}
