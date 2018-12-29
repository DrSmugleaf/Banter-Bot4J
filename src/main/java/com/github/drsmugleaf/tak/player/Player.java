package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public abstract class Player {

    @NotNull
    private final String NAME;

    @NotNull
    private final Game GAME;

    @NotNull
    private final Hand HAND;

    public Player(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        NAME = name;
        HAND = new Hand(color, preset);
        GAME = game;
    }

    @NotNull
    public final String getName() {
        return NAME;
    }

    @NotNull
    public final Game getGame() {
        return GAME;
    }

    @NotNull
    public final Hand getHand() {
        return HAND;
    }

    public final boolean canPlace(@NotNull Type type, int row, int column) {
        return getHand().has(type) && GAME.canPlace(this, type, row, column);
    }

    @NotNull
    public final Square place(@NotNull Type type, int row, int column) {
        return GAME.place(this, type, row, column);
    }

    public final void surrender() {
        GAME.surrender(this);
    }

    public abstract void nextTurn();

}
