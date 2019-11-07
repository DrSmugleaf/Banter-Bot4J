package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.ICoordinates;
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

    public GuiPlayer(String name, GuiGame game, Color color, Preset preset) {
        super(name, game, color, true);

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
    public ICoordinates getNextAction() {
        return null;
    }

}
