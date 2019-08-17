package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.pieces.Type;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by DrSmugleaf on 23/01/2019
 */
public class GuiMouseListener implements MouseListener {

    private final GuiPlayer PLAYER;
    private final SquareButton SQUARE;

    GuiMouseListener(GuiPlayer player, SquareButton button) {
        PLAYER = player;
        SQUARE = button;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        JButton button = SQUARE.getButton();
        Type type;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                type = Type.FLAT_STONE;
                break;
            case MouseEvent.BUTTON2:
                type = Type.CAPSTONE;
                break;
            case MouseEvent.BUTTON3:
                type = Type.STANDING_STONE;
                break;
            default:
                return;
        }

        button.getModel().setArmed(true);
        button.getModel().setPressed(true);

        Coordinates coordinates = new Coordinates(SQUARE.getColumn(), SQUARE.getRow(), type);
        if (coordinates.canPlace(PLAYER)) {
            PLAYER.setNextMove(coordinates);
            synchronized (PLAYER) {
                PLAYER.notify();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = SQUARE.getButton();
        button.getModel().setArmed(false);
        button.getModel().setPressed(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
