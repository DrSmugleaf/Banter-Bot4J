package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import javax.swing.*;

/**
 * Created by DrSmugleaf on 31/12/2018
 */
public class GuiPlayer extends Player {

    private final GuiGame GAME;
    @Nullable
    private Coordinates NEXT_MOVE;

    public GuiPlayer(String name, GuiGame game, Color color, Preset preset) {
        super(name, game, color, preset);

        GAME = game;

        SquareButton[][] pieces = GAME.getBoardPanel().getButtons();
        for (SquareButton[] row : pieces) {
            for (SquareButton square : row) {
                JButton button = square.getButton();
                button.addMouseListener(new GuiMouseListener(this, square));
            }
        }
    }

    public static Player from(PlayerInformation<GuiGame> information) {
        return new GuiPlayer(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public void nextTurn() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            surrender();
            return;
        }

        if (NEXT_MOVE == null) {
            surrender();
            return;
        }

        NEXT_MOVE.place(this);
    }

    protected void setNextMove(Coordinates nextMove) {
        NEXT_MOVE = nextMove;

        synchronized (this) {
            notify();
        }
    }

}
