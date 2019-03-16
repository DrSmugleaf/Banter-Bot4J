package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.bot.minimax.MinimaxFlatMoveBot;
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
    private final BoardPanel BOARD_PANEL;

    @NotNull
    private JFrame FRAME;

    private GuiGame(@NotNull BoardPanel board, @NotNull Preset preset, @NotNull String playerName1, @NotNull String playerName2, @NotNull Function<PlayerInformation, Player> playerMaker1, @NotNull Function<PlayerInformation, Player> playerMaker2) {
        super(board, preset, playerName1, playerName2, playerMaker1, playerMaker2);
        BOARD_PANEL = board;
        FRAME = setupFrame();
    }

    public GuiGame(@NotNull Preset preset, @NotNull String playerName1, @NotNull String playerName2, @NotNull Function<PlayerInformation, Player> playerMaker1, @NotNull Function<PlayerInformation, Player> playerMaker2) {
        this(BoardPanel.from(preset), preset, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    public static void main(String[] args) {
        GuiGame game = new GuiGame(Preset.getDefault(), "1", "2", MinimaxFlatMoveBot::from, MinimaxFlatMoveBot::from);
        game.start();
    }

    @NotNull
    private JFrame setupFrame() {
        JFrame frame = new JFrame("Tak");

        SwingUtilities.invokeLater(() -> {
            frame.setIconImage(APPLICATION_ICON);
            frame.add(BOARD_PANEL.getGui());
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationByPlatform(true);

            frame.pack();

            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        });

        return frame;
    }

    @NotNull
    protected BoardPanel getBoardPanel() {
        return BOARD_PANEL;
    }

    @NotNull
    protected JFrame getFrame() {
        return FRAME;
    }

}
