package com.github.drsmugleaf.tak.gui;

import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.board.layout.Preset;
import com.github.drsmugleaf.tak.bot.minimax.MinimaxFlatMoveBot;
import com.github.drsmugleaf.tak.images.Images;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import com.google.common.collect.ImmutableMap;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class GuiGame extends Game {

    private static final Image APPLICATION_ICON = Images.getLogo();
    private final ImmutableMap<IColor, IPlayer> PLAYERS;
    private final BoardPanel BOARD_PANEL;
    private JFrame FRAME;

    private GuiGame(BoardPanel board, String playerName1, String playerName2, Function<IPlayerInformation, IPlayer> playerMaker1, Function<IPlayerInformation, IPlayer> playerMaker2) {
        super(board);
        BOARD_PANEL = board;
        FRAME = setupFrame();
        IPlayer player1 = playerMaker1.apply(new PlayerInformation(playerName1, this, Color.BLACK));
        IPlayer player2 = playerMaker2.apply(new PlayerInformation(playerName2, this, Color.WHITE));
        PLAYERS = ImmutableMap.of(player1.getColor(), player1, player2.getColor(), player2);
        nextPlayer = player1;
    }

    public GuiGame(IPreset preset, String playerName1, String playerName2, Function<IPlayerInformation, IPlayer> playerMaker1, Function<IPlayerInformation, IPlayer> playerMaker2) {
        this(BoardPanel.from(preset), playerName1, playerName2, playerMaker1, playerMaker2);
    }

    public static void main(String[] args) {
        GuiGame game = new GuiGame(Preset.getDefault(), "1", "2", GuiPlayer::from, MinimaxFlatMoveBot::from);
        game.start();
    }

    @Override
    public ImmutableMap<IColor, IPlayer> getPlayers() {
        return PLAYERS;
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
