package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.bot.RandomFlatBot;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class GuiGame extends Game {

    @NotNull
    private final BoardPanel BOARD;

    @NotNull
    private final JFrame FRAME;

    public GuiGame(@NotNull Preset preset, @NotNull String player1, @NotNull String player2) {
        super(preset, player1, player2, RandomFlatBot::from);
        BOARD = new BoardPanel(preset);
        FRAME = setupFrame();
    }

    @NotNull
    private JFrame setupFrame() {
        JFrame frame = new JFrame("Tak");

        SwingUtilities.invokeLater(() -> {
            frame.add(BOARD.gui);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationByPlatform(true);

            frame.pack();

            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);

            start();
        });

        return frame;
    }

    @NotNull
    @Override
    public Square place(@NotNull Player player, @NotNull Type type, int row, int column) {
        Square square = super.place(player, type, row, column);
        BOARD.pieces[row][column].update(square);
        return square;
    }

}
