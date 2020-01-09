package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.IPreset;
import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.bot.minimax.MinimaxFlatMoveBot;
import com.github.drsmugleaf.tak.images.Images;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class GuiGame extends Game {

    private static final Image APPLICATION_ICON = Images.getLogo();
    private final BoardPanel BOARD_PANEL;
    private JFrame FRAME;

    private GuiGame(BoardPanel board, String playerName1, String playerName2, Function<PlayerInformation, Player> playerMaker1, Function<PlayerInformation, Player> playerMaker2) {
        super(board, playerName1, playerName2, playerMaker1, playerMaker2);
        BOARD_PANEL = board;
        FRAME = setupFrame();
    }

    public GuiGame(IPreset preset, String playerName1, String playerName2, Function<PlayerInformation, Player> playerMaker1, Function<PlayerInformation, Player> playerMaker2) {
        this(BoardPanel.from(preset), playerName1, playerName2, playerMaker1, playerMaker2);
    }

    public static void main(String[] args) {
        GuiGame game = new GuiGame(Preset.getDefault(), "1", "2", MinimaxFlatMoveBot::from, MinimaxFlatMoveBot::from);
        game.start();
    }

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

    protected BoardPanel getBoardPanel() {
        return BOARD_PANEL;
    }

    protected JFrame getFrame() {
        return FRAME;
    }

}
