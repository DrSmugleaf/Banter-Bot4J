package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.IGame;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public abstract class Player {

    private final String NAME;
    private final IGame GAME;
    private final Hand HAND;
    @Nullable
    private ICoordinates NEXT_ACTION = null;
    private final boolean PASSIVE;
    @Nullable
    private List<ICoordinates> AVAILABLE_ACTIONS = null;

    public Player(String name, IGame game, Color color, boolean passive) {
        NAME = name;
        HAND = new Hand(color, game.getBoard().getPreset());
        GAME = game;
        PASSIVE = passive;
    }

    public final List<ICoordinates> getAvailableActions(IBoard board, Type type) {
        List<ICoordinates> moves = getAvailableMoves(board);
        List<ICoordinates> places = getAvailablePlaces(board, type);
        moves.addAll(places);

        return moves;
    }

    public List<ICoordinates> getAvailableActions(IBoard board) {
        List<ICoordinates> moves = getAvailableMoves(board);
        List<ICoordinates> places = getAvailablePlaces(board);
        moves.addAll(places);

        return moves;
    }

    public final List<ICoordinates> getAvailableActions() {
        if (AVAILABLE_ACTIONS != null) {
            return AVAILABLE_ACTIONS;
        }

        List<ICoordinates> actions = getAvailableActions(getGame().getBoard());
        AVAILABLE_ACTIONS = actions;
        return actions;
    }

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

    public final List<ICoordinates> getAvailableMoves() {
        return getAvailableMoves(getGame().getBoard());
    }

    public final List<ICoordinates> getAvailablePlaces(IBoard board, Type... types) {
        List<ICoordinates> places = new ArrayList<>();

        Line[] rows = board.getRows();
        for (int i = 0; i < rows.length; i++) {
            ISquare[] row = rows[i].getSquares();
            for (int j = 0; j < row.length; j++) {
                for (Type type : types) {
                    if (canPlace(type, j, i)) {
                        places.add(new Coordinates(j, i, type));
                    }
                }
            }
        }

        return places;
    }

    public final List<ICoordinates> getAvailablePlaces(IBoard board, Set<Type> types) {
        return getAvailablePlaces(board, types.toArray(new Type[0]));
    }

    public final List<ICoordinates> getAvailablePlaces(IBoard board) {
        return getAvailablePlaces(board, Type.getTypes());
    }

    public final List<ICoordinates> getAvailablePlaces(Type type) {
        return getAvailablePlaces(getGame().getBoard(), type);
    }

    public final List<ICoordinates> getAvailablePlaces() {
        return getAvailablePlaces(getGame().getBoard(), Type.getTypes());
    }

    public final String getName() {
        return NAME;
    }

    public final IGame getGame() {
        return GAME;
    }

    public final Hand getHand() {
        return HAND;
    }

    public final boolean isPassive() {
        return PASSIVE;
    }

    @Nullable
    public abstract ICoordinates getNextAction();

    public final void setNextAction(ICoordinates action) {
        NEXT_ACTION = action;
        synchronized (this) {
            notify();
        }
    }

    public final Color getColor() {
        return getHand().getColor();
    }

    public final boolean canMove(ISquare origin, ISquare destination, int pieces) {
        return getColor() == origin.getColor() && GAME.canMove(this, origin, destination, pieces);
    }

    public final boolean canMove(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces) {
        Line[] rows = getGame().getBoard().getRows();
        ISquare origin = rows[originRow].getSquares()[originColumn];
        ISquare destination = rows[destinationRow].getSquares()[destinationColumn];
        return canMove(origin, destination, pieces);
    }

    public final ISquare move(ISquare origin, ISquare destination, int pieces) {
        return GAME.move(this, origin, destination, pieces);
    }

    public final ISquare move(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces) {
        Line[] rows = getGame().getBoard().getRows();
        ISquare origin = rows[originRow].getSquares()[originColumn];
        ISquare destination = rows[destinationRow].getSquares()[destinationColumn];
        return move(origin, destination, pieces);
    }

    public final boolean canPlace(Type type, int column, int row) {
        return getHand().has(type) && GAME.canPlace(this, column, row);
    }

    public final ISquare place(Type type, int column, int row) {
        return GAME.place(this, type, column, row);
    }

    public final void surrender() {
        AVAILABLE_ACTIONS = null;
        if (GAME.isActive()) {
            GAME.surrender(this);
        }
    }

    public final void resetPlayer() {
        HAND.reset();
        NEXT_ACTION = null;
        AVAILABLE_ACTIONS = null;
        synchronized (this) {
            notify();
        }
    }

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

    public void onEnemyPieceMove(Player player, ISquare origin, ISquare destination, int pieces) {}

    public void onOwnPieceMove(ISquare origin, ISquare destination, int pieces) {}

    public void onEnemyPiecePlace(Player player, Type type, ISquare square) {}

    public void onOwnPiecePlace(Type type, ISquare square) {}

    public void onEnemyTurnEnd(Player player) {}

    public void onOwnTurnEnd() {}

    public void onGameEnd(@Nullable Player winner) {}

}
