package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.action.*;
import com.github.drsmugleaf.tak.board.layout.IAdjacentSquares;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.board.layout.Line;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.pieces.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public final List<IAction> getAvailableActions(IBoard board, IType type) {
        List<IAction> actions = new ArrayList<>();
        List<IMove> moves = getAvailableMoves(board);
        List<IPlace> places = getAvailablePlaces(board, type);
        actions.addAll(moves);
        actions.addAll(places);

        return actions;
    }

    @Override
    public List<IAction> getAvailableActions(IBoard board) {
        List<IAction> actions = new ArrayList<>();
        List<IMove> moves = getAvailableMoves(board);
        List<IPlace> places = getAvailablePlaces(board);
        actions.addAll(moves);
        actions.addAll(places);

        return actions;
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
    public final List<IMove> getAvailableMoves(IBoard board) {
        List<IMove> moves = new ArrayList<>();

        for (Line row : board.getRows()) {
            for (ISquare origin : row.getSquares()) {
                if (origin.getTopPiece() == null) {
                    continue;
                }

                IAdjacentSquares adjacent = board.getAdjacent(origin);

                for (ISquare destination : adjacent.getAll()) {
                    if (destination == null) {
                        continue;
                    }

                    for (int amount = 1; amount <= board.getPreset().getCarryLimit(); amount++) {
                        IMove move = new Move(origin, destination, amount);
                        if (move.canExecute(this)) {
                            moves.add(move);
                        }
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public final List<IMove> getAvailableMoves() {
        return getAvailableMoves(getGame().getBoard());
    }

    @Override
    public final List<IPlace> getAvailablePlaces(IBoard board, IType... types) {
        List<IPlace> places = new ArrayList<>();

        Line[] rows = board.getRows();
        for (int i = 0; i < rows.length; i++) {
            ISquare[] row = rows[i].getSquares();
            for (int j = 0; j < row.length; j++) {
                for (IType type : types) {
                    IPlace place = new Place(i, j, type);
                    if (canPlace(place)) {
                        places.add(place);
                    }
                }
            }
        }

        return places;
    }

    @Override
    public final List<IPlace> getAvailablePlaces(IBoard board, Set<IType> types) {
        return getAvailablePlaces(board, types.toArray(new IType[0]));
    }

    @Override
    public final List<IPlace> getAvailablePlaces(IBoard board) {
        return getAvailablePlaces(board, Type.getTypes());
    }

    @Override
    public final List<IPlace> getAvailablePlaces(IType type) {
        return getAvailablePlaces(getGame().getBoard(), type);
    }

    @Override
    public final List<IPlace> getAvailablePlaces() {
        return getAvailablePlaces(getGame().getBoard(), Type.getTypes());
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
        synchronized (this) {
            notify();
        }
    }

    @Override
    public final IColor getColor() {
        return getHand().getColor();
    }

    @Override
    public final boolean canMove(IMove move) {
        return getColor() == move.toSquare(getGame().getBoard()).getColor() && GAME.canMove(this, move);
    }

    @Override
    public final ISquare move(IMove move, boolean silent) {
        return GAME.move(this, move, silent);
    }

    @Override
    public final boolean canPlace(IPlace place) {
        return getHand().has(place.getType()) && GAME.canPlace(this, place);
    }

    @Override
    public final ISquare place(IPlace place, boolean silent) {
        return GAME.place(this, place, silent);
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
//        if (isPassive()) {
//            try {
//                synchronized (this) {
//                    wait();
//                }
//            } catch (InterruptedException e) {
//                throw new IllegalStateException("Player thread interrupted", e);
//            }
//        }

        if (NEXT_ACTION == null) {
            NEXT_ACTION = getNextAction();
        }

        if (NEXT_ACTION == null) {
            surrender();
        } else {
            NEXT_ACTION.execute(this);
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
