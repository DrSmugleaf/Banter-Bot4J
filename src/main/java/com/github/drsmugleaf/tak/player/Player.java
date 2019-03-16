package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    public final List<Coordinates> getAvailableActions(@NotNull Board board, @NotNull Type type) {
        List<MovingCoordinates> moves = getAvailableMoves();
        List<Coordinates> places = getAvailablePlaces(board, type);
        places.addAll(moves);

        return places;
    }

    @NotNull
    public List<Coordinates> getAvailableActions(@NotNull Board board) {
        List<MovingCoordinates> moves = getAvailableMoves(board);
        List<Coordinates> places = getAvailablePlaces(board);
        places.addAll(moves);

        return places;
    }

    @NotNull
    public final List<Coordinates> getAvailableActions() {
        return getAvailableActions(getGame().getBoard());
    }

    @NotNull
    public final List<MovingCoordinates> getAvailableMoves(@NotNull Board board) {
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

    @NotNull
    public final List<MovingCoordinates> getAvailableMoves() {
        return getAvailableMoves(getGame().getBoard());
    }

    @NotNull
    public final List<Coordinates> getAvailablePlaces(@NotNull Board board, @NotNull Type type) {
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

    @NotNull
    public final List<Coordinates> getAvailablePlaces(@NotNull Board board, @NotNull List<Type> types) {
        List<Coordinates> places = new ArrayList<>();

        for (Type type : types) {
            places.addAll(getAvailablePlaces(board, type));
        }

        return places;
    }

    @NotNull
    public final List<Coordinates> getAvailablePlaces(@NotNull Board board) {
        return getAvailablePlaces(board, Type.getTypes());
    }

    @NotNull
    public final List<Coordinates> getAvailablePlaces(@NotNull Type type) {
        return getAvailablePlaces(getGame().getBoard(), type);
    }

    @NotNull
    public final List<Coordinates> getAvailablePlaces() {
        return getAvailablePlaces(getGame().getBoard(), Type.getTypes());
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

    @NotNull
    public final Color getColor() {
        return getHand().getColor();
    }

    public final boolean canMove(@NotNull Square origin, @NotNull Square destination, int pieces) {
        return origin.getColor() == getColor() && GAME.canMove(this, origin, destination, pieces);
    }

    @NotNull
    public final Square move(@NotNull Square origin, @NotNull Square destination, int pieces) {
        return GAME.move(this, origin, destination, pieces);
    }

    public final boolean canPlace(@NotNull Type type, int column, int row) {
        return getHand().has(type) && GAME.canPlace(this, column, row);
    }

    @NotNull
    public final Square place(@NotNull Type type, int column, int row) {
        return GAME.place(this, type, column, row);
    }

    public final void surrender() {
        GAME.surrender(this);
    }

    public abstract void nextTurn();

}
