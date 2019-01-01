package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        GuiPlayer player = this;
        for (int rowIndex = 0; rowIndex < pieces.length; rowIndex++) {
            SquareButton[] row = pieces[rowIndex];
            for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                SquareButton square = row[columnIndex];
                int finalRowIndex = rowIndex;
                int finalColumnIndex = columnIndex;

                JButton button = square.getButton();
                button.addActionListener(e -> {
                    if (game.getNextPlayer() == this) {
                        NEXT_MOVE = new Coordinates(finalRowIndex, finalColumnIndex, Type.FLAT_STONE);
                        if (NEXT_MOVE.canPlace(this)) {
                            synchronized (this) {
                                this.notify();
                            }
                        }
                    }
                });

                button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        button.getModel().setArmed(true);
                        button.getModel().setPressed(true);

                        Type type;
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            type = Type.STANDING_STONE;
                        } else if (e.getButton() == MouseEvent.BUTTON2) {
                            type = Type.CAPSTONE;
                        } else {
                            return;
                        }

                        NEXT_MOVE = new Coordinates(finalRowIndex, finalColumnIndex, type);
                        if (NEXT_MOVE.canPlace(player)) {
                            synchronized (player) {
                                player.notify();
                            }
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button.getModel().setArmed(false);
                        button.getModel().setPressed(false);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

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

}
