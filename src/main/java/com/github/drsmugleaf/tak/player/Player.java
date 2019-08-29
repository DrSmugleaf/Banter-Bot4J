package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public abstract class Player {

    private final String NAME;
    private final Game GAME;
    private final Hand HAND;

    public Player(String name, Game game, Color color, Preset preset) {
        NAME = name;
        HAND = new Hand(color, preset);
        GAME = game;
    }

    public final List<ICoordinates> getAvailableActions(Board board, Type type) {
        List<ICoordinates> moves = getAvailableMoves(board);
        List<ICoordinates> places = getAvailablePlaces(board, type);
        moves.addAll(places);

        return moves;
    }

    public List<ICoordinates> getAvailableActions(Board board) {
        List<ICoordinates> moves = getAvailableMoves(board);
        List<ICoordinates> places = getAvailablePlaces(board);
        moves.addAll(places);

        return moves;
    }

    public final List<ICoordinates> getAvailableActions() {
        return getAvailableActions(getGame().getBoard());
    }

    public final List<ICoordinates> getAvailableMoves(Board board) {
        List<ICoordinates> moves = new ArrayList<>();

        for (Line row : board.getRows()) {
            for (Square origin : row.getSquares()) {
                AdjacentSquares adjacent = GAME.getBoard().getAdjacent(origin);

                for (Square destination : adjacent.getAll()) {
                    if (destination == null) {
                        continue;
                    }

                    for (int amount = 1; amount < GAME.getBoard().getPreset().getCarryLimit(); amount++) {
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

    public final List<ICoordinates> getAvailablePlaces(Board board, Type... types) {
        List<ICoordinates> places = new ArrayList<>();

        Line[] rows = board.getRows();
        for (int i = 0; i < rows.length; i++) {
            Square[] row = rows[i].getSquares();
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

    public final List<ICoordinates> getAvailablePlaces(Board board, Set<Type> types) {
        return getAvailablePlaces(board, types.toArray(new Type[0]));
    }

    public final List<ICoordinates> getAvailablePlaces(Board board) {
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

    public final Game getGame() {
        return GAME;
    }

    public final Hand getHand() {
        return HAND;
    }

    public final Color getColor() {
        return getHand().getColor();
    }

    public final boolean canMove(Square origin, Square destination, int pieces) {
        return getColor() == origin.getColor() && GAME.canMove(this, origin, destination, pieces);
    }

    public final Square move(Square origin, Square destination, int pieces) {
        return GAME.move(this, origin, destination, pieces);
    }

    public final boolean canPlace(Type type, int column, int row) {
        return getHand().has(type) && GAME.canPlace(this, column, row);
    }

    public final Square place(Type type, int column, int row) {
        return GAME.place(this, type, column, row);
    }

    public final void surrender() {
        if (GAME.isActive()) {
            GAME.surrender(this);
        }
    }

    public final void resetPlayer() {
        HAND.reset();
    }

    public abstract void nextTurn();

    public void onEnemyPieceMove(Player player, Square origin, Square destination, int pieces) {}

    public void onOwnPieceMove(Square origin, Square destination, int pieces) {}

    public void onEnemyPiecePlace(Player player, Type type, Square square) {}

    public void onOwnPiecePlace(Type type, Square square) {}

    public void onEnemyTurnEnd(Player player) {}

    public void onOwnTurnEnd() {}

    public void onGameEnd(@Nullable Player winner) {}

}
