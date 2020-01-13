package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import com.github.drsmugleaf.tak.player.Player;

import javax.swing.*;

/**
 * Created by DrSmugleaf on 31/12/2018
 */
public class GuiPlayer extends Player {

    private final GuiGame GAME;

    public GuiPlayer(String name, Game game, IColor color) {
        super(name, game, color, true);

        GAME = (GuiGame) game;

        SquareButton[][] pieces = GAME.getBoardPanel().getButtons();
        for (SquareButton[] column : pieces) {
            for (SquareButton square : column) {
                JButton button = square.getButton();
                button.addMouseListener(new GuiMouseListener(this, square));
            }
        }
    }

    public static IPlayer from(IPlayerInformation information) {
        return new GuiPlayer(information.getName(), information.getGame(), information.getColor());
    }

    @Override
    public IAction getNextAction() {
        return null;
    }

}
