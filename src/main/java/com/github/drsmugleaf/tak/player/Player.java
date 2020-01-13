package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
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
    private ICoordinates NEXT_ACTION = null;
    private final boolean PASSIVE;
    @Nullable
    private List<ICoordinates> AVAILABLE_ACTIONS = null;

    public Player(String name, IGame game, IColor color, boolean passive) {
        NAME = name;
        HAND = new Hand(color, game.getBoard().getPreset());
        GAME = game;
        PASSIVE = passive;
    }

    @Override
    public final List<ICoordinates> getAvailableActions(IBoard board, IType type) {
        List<ICoordinates> moves = getAvailableMoves(board);
        List<ICoordinates> places = getAvailablePlaces(board, type);
        moves.addAll(places);

        return moves;
    }

    @Override
    public List<ICoordinates> getAvailableActions(IBoard board) {
        List<ICoordinates> moves = getAvailableMoves(board);
        List<ICoordinates> places = getAvailablePlaces(board);
        moves.addAll(places);

        return moves;
    }

    @Override
    public final List<ICoordinates> getAvailableActions() {
        if (AVAILABLE_ACTIONS != null) {
            return AVAILABLE_ACTIONS;
        }

        List<ICoordinates> actions = getAvailableActions(getGame().getBoard());
        AVAILABLE_ACTIONS = actions;
        return actions;
    }

    @Override
    public final List<ICoordinates> getAvailableMoves(IBoard board) {
        List<ICoordinates> moves = new ArrayList<>();

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
                        if (canMove(origin, destination, amount)) {
                            moves.add(new MovingCoordinates(origin, destination, amount));
                        }
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public final List<ICoordinates> getAvailableMoves() {
        return getAvailableMoves(getGame().getBoard());
    }

    @Override
    public final List<ICoordinates> getAvailablePlaces(IBoard board, IType... types) {
        List<ICoordinates> places = new ArrayList<>();

        Line[] rows = board.getRows();
        for (int i = 0; i < rows.length; i++) {
            ISquare[] row = rows[i].getSquares();
            for (int j = 0; j < row.length; j++) {
                for (IType type : types) {
                    if (canPlace(type, j, i)) {
                        places.add(new Coordinates(j, i, type));
                    }
                }
            }
        }

        return places;
    }

    @Override
    public final List<ICoordinates> getAvailablePlaces(IBoard board, Set<IType> types) {
        return getAvailablePlaces(board, types.toArray(new IType[0]));
    }

    @Override
    public final List<ICoordinates> getAvailablePlaces(IBoard board) {
        return getAvailablePlaces(board, Type.getTypes());
    }

    @Override
    public final List<ICoordinates> getAvailablePlaces(IType type) {
        return getAvailablePlaces(getGame().getBoard(), type);
    }

    @Override
    public final List<ICoordinates> getAvailablePlaces() {
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
    public final void setNextAction(ICoordinates action) {
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
    public final boolean canMove(ISquare origin, ISquare destination, int pieces) {
        return getColor() == origin.getColor() && GAME.canMove(this, origin, destination, pieces);
    }

    @Override
    public final boolean canMove(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces) {
        Line[] rows = getGame().getBoard().getRows();
        ISquare origin = rows[originRow].getSquares()[originColumn];
        ISquare destination = rows[destinationRow].getSquares()[destinationColumn];
        return canMove(origin, destination, pieces);
    }

    @Override
    public final ISquare move(ISquare origin, ISquare destination, int pieces) {
        return GAME.move(this, origin, destination, pieces);
    }

    @Override
    public final ISquare move(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces) {
        Line[] rows = getGame().getBoard().getRows();
        ISquare origin = rows[originRow].getSquares()[originColumn];
        ISquare destination = rows[destinationRow].getSquares()[destinationColumn];
        return move(origin, destination, pieces);
    }

    @Override
    public final boolean canPlace(IType type, int column, int row) {
        return getHand().has(type) && GAME.canPlace(this, column, row);
    }

    @Override
    public final ISquare place(IType type, int column, int row) {
        return GAME.place(this, type, column, row);
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
            NEXT_ACTION.place(this);
        }

        NEXT_ACTION = null;
        AVAILABLE_ACTIONS = null;
    }

    @Override
    public void onEnemyPieceMove(IPlayer player, ISquare origin, ISquare destination, int pieces) {}

    @Override
    public void onOwnPieceMove(ISquare origin, ISquare destination, int pieces) {}

    @Override
    public void onEnemyPiecePlace(IPlayer player, IType type, ISquare square) {}

    @Override
    public void onOwnPiecePlace(IType type, ISquare square) {}

    @Override
    public void onEnemyTurnEnd(IPlayer player) {}

    @Override
    public void onOwnTurnEnd() {}

    @Override
    public void onGameEnd(@Nullable IPlayer winner) {}

}
