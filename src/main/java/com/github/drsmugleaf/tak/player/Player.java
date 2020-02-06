package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public abstract class Player implements IPlayer {

    private final String NAME;
    private final IGame GAME;
    private final Hand HAND;
    @Nullable
    private IAction NEXT_ACTION = null;
    private final boolean PASSIVE;
    @Nullable
    private List<IAction> AVAILABLE_ACTIONS = null;

    public Player(String name, IGame game, IColor color, boolean passive) {
        NAME = name;
        HAND = new Hand(color, game.getBoard().getPreset());
        GAME = game;
        PASSIVE = passive;
    }

    @Override
    public final List<IAction> getAvailableActions() {
        if (AVAILABLE_ACTIONS != null) {
            return AVAILABLE_ACTIONS;
        }

        List<IAction> actions = getAvailableActions(getGame().getBoard());
        AVAILABLE_ACTIONS = actions;
        return actions;
    }

    @Override
    public final List<IAction> getAvailableActions(IBoard board) {
        return board.getPreset().getAllActions().stream().filter(action -> action.canExecute(this, board)).collect(Collectors.toList());
    }

    @Override
    public final String getName() {
        return NAME;
    }

    @Override
    public final IGame getGame() {
        return GAME;
    }

    @Override
    public final Hand getHand() {
        return HAND;
    }

    @Override
    public final boolean isPassive() {
        return PASSIVE;
    }

    @Override
    public final void setNextAction(IAction action) {
        NEXT_ACTION = action;

        if (isPassive()) {
            synchronized (this) {
                notify();
            }
        }
    }

    @Override
    public final IColor getColor() {
        return getHand().getColor();
    }

    @Override
    public final boolean canMove(IMove move) {
        return canMove(move, getGame().getBoard());
    }

    @Override
    public boolean canMove(IMove move, IBoard board) {
        return getColor() == move.getCoordinates().get(0).toSquare(board).getColor() && GAME.canMove(this, move, board);
    }

    @Override
    public final int move(IMove move, boolean silent) {
        if (!canMove(move)) {
            throw new IllegalArgumentException();
        }

        int previousState = GAME.move(this, move, silent);
        AVAILABLE_ACTIONS = null;
        return previousState;
    }

    @Override
    public final boolean canPlace(IPlace place) {
        return canPlace(place, getGame().getBoard());
    }

    @Override
    public boolean canPlace(IPlace place, IBoard board) {
        return getHand().has(place.getType()) && GAME.canPlace(this, place, board);
    }

    @Override
    public final int place(IPlace place, boolean silent) {
        if (!canPlace(place)) {
            throw new IllegalArgumentException();
        }

        int previousState = GAME.place(this, place, silent);
        AVAILABLE_ACTIONS = null;
        return previousState;
    }

    @Override
    public final void surrender() {
        AVAILABLE_ACTIONS = null;
        NEXT_ACTION = null;
        if (GAME.isActive()) {
            GAME.surrender(this);
        }
    }

    @Override
    public final void resetPlayer() {
        HAND.reset();
        NEXT_ACTION = null;
        AVAILABLE_ACTIONS = null;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public final void nextTurn() {
        if (isPassive()) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException("Player thread interrupted", e);
            }
        }

        if (NEXT_ACTION == null) {
            NEXT_ACTION = getNextAction();
        }

        if (NEXT_ACTION == null) {
            surrender();
        } else {
            NEXT_ACTION.execute(this, false);
        }

        NEXT_ACTION = null;
        AVAILABLE_ACTIONS = null;
    }

    @Override
    public void onEnemyPieceMove(IPlayer player, IMove move) {}

    @Override
    public void onOwnPieceMove(IMove move) {}

    @Override
    public void onEnemyPiecePlace(IPlayer player, IPlace place) {}

    @Override
    public void onOwnPiecePlace(IPlace place) {}

    @Override
    public void onEnemyTurnEnd(IPlayer player) {}

    @Override
    public void onOwnTurnEnd() {}

    @Override
    public void onGameEnd(@Nullable IPlayer winner) {}

}
