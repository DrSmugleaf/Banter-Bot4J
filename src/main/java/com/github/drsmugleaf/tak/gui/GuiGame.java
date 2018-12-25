package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class GuiGame extends Game {

    private final BoardPanel BOARD;
    private final JFrame FRAME;

    public GuiGame(@NotNull Board board, @NotNull Collection<Player> players) {
        super(board, players);
        BOARD = new BoardPanel(board.getPreset());
        FRAME = setupFrame();
    }

    private JFrame setupFrame() {
        JFrame frame = new JFrame("Tak");

        SwingUtilities.invokeLater(() -> {
            frame.add(BOARD.gui);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationByPlatform(true);

            frame.pack();

            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        });

        return frame;
    }

    @Override
    public @NotNull List<Player> getPlayers() {
        return super.getPlayers();
    }

    @Override
    public boolean canPlace(@NotNull Player player, @NotNull Type type, int row, int square) {
        return super.canPlace(player, type, row, square);
    }

    @Override
    public boolean place(@NotNull Player player, @NotNull Type type, int row, int square) {
        return super.place(player, type, row, square);
    }

    @Override
    public @Nullable Player checkVictory() {
        return super.checkVictory();
    }

}
