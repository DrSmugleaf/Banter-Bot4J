package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class GuiGame extends Game {

    @NotNull
    private static final Image APPLICATION_ICON = Images.get("logo.jpg");

    @NotNull
    protected BoardPanel BOARD_PANEL;

    @NotNull
    protected JFrame FRAME;

    public GuiGame(@NotNull Preset preset, @NotNull String playerName1, @NotNull String playerName2, @NotNull Function<PlayerInformation, Player> playerMaker1, @NotNull Function<PlayerInformation, Player> playerMaker2) {
        super(preset, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    @Override
    protected void setup(@NotNull Preset preset, @NotNull String playerName1, @NotNull String playerName2, @NotNull Function<PlayerInformation, Player> playerMaker1, @NotNull Function<PlayerInformation, Player> playerMaker2) {
        BOARD_PANEL = new BoardPanel(preset);
        FRAME = setupFrame();
        super.setup(preset, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    @NotNull
    private JFrame setupFrame() {
        JFrame frame = new JFrame("Tak");

        SwingUtilities.invokeLater(() -> {
            frame.setIconImage(APPLICATION_ICON);
            frame.add(BOARD_PANEL.gui);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationByPlatform(true);

            frame.pack();

            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        });

        return frame;
    }

    @Override
    public void onPieceMove(@NotNull Player player, @NotNull Square origin, @NotNull Square destination, int pieces) {
        BOARD_PANEL.pieces[origin.getRow()][origin.getColumn()].update(origin);
        BOARD_PANEL.pieces[destination.getRow()][destination.getColumn()].update(destination);
    }

    @Override
    public void onPiecePlace(@NotNull Player player, @NotNull Type type, @NotNull Square square) {
        int column = square.getColumn();
        int row = square.getRow();
        BOARD_PANEL.pieces[row][column].update(square);
    }

}
