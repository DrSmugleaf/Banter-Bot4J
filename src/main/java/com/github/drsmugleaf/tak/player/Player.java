package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public final List<Coordinates> getAvailableActions(Board board, Type type) {
        List<MovingCoordinates> moves = getAvailableMoves();
        List<Coordinates> places = getAvailablePlaces(board, type);
        places.addAll(moves);

        return places;
    }

    public List<Coordinates> getAvailableActions(Board board) {
        List<MovingCoordinates> moves = getAvailableMoves(board);
        List<Coordinates> places = getAvailablePlaces(board);
        places.addAll(moves);

        return places;
    }

    public final List<Coordinates> getAvailableActions() {
        return getAvailableActions(getGame().getBoard());
    }

    public final List<MovingCoordinates> getAvailableMoves(Board board) {
        List<MovingCoordinates> moves = new ArrayList<>();

        Line[] rows = board.getRows();
        for (Line row : rows) {
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

    public final List<MovingCoordinates> getAvailableMoves() {
        return getAvailableMoves(getGame().getBoard());
    }

    public final List<Coordinates> getAvailablePlaces(Board board, Type type) {
        List<Coordinates> places = new ArrayList<>();

        Line[] rows = board.getRows();
        for (int i = 0; i < rows.length; i++) {
            Square[] row = rows[i].getSquares();
            for (int j = 0; j < row.length; j++) {
                if (canPlace(type, j, i)) {
                    places.add(new Coordinates(j, i, type));
                }
            }
        }

        return places;
    }

    public final List<Coordinates> getAvailablePlaces(Board board, List<Type> types) {
        List<Coordinates> places = new ArrayList<>();

        for (Type type : types) {
            places.addAll(getAvailablePlaces(board, type));
        }

        return places;
    }

    public final List<Coordinates> getAvailablePlaces(Board board) {
        return getAvailablePlaces(board, Type.getTypes());
    }

    public final List<Coordinates> getAvailablePlaces(Type type) {
        return getAvailablePlaces(getGame().getBoard(), type);
    }

    public final List<Coordinates> getAvailablePlaces() {
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
        return origin.getColor() == getColor() && GAME.canMove(this, origin, destination, pieces);
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
        GAME.surrender(this);
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
