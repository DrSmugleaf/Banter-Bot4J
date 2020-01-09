package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import com.github.drsmugleaf.tak.player.Player;

import javax.swing.*;

/**
 * Created by DrSmugleaf on 31/12/2018
 */
public class GuiPlayer extends Player {

    private final GuiGame GAME;

    public GuiPlayer(String name, GuiGame game, Color color) {
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

    public static IPlayer from(IPlayerInformation information, GuiGame game) {
        return new GuiPlayer(information.getName(), game, information.getColor());
    }

    @Override
    public ICoordinates getNextAction() {
        return null;
    }

}
