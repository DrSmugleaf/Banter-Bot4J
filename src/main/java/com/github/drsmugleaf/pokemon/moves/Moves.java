package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.pokemon.battle.Action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 19/10/2017.
 */
public class Moves {

    @Nonnull
    private final List<Move> MOVES;

    @Nonnull
    private final List<BaseMove> VALID_MOVES = new ArrayList<>();

    @Nonnull
    private final Map<Move, Integer> DISABLED_MOVES = new HashMap<>();

    public Moves(@Nonnull List<Move> moves) {
        MOVES = new ArrayList<>(moves);
        for (Move move : MOVES) {
            VALID_MOVES.add(move.getBaseMove());
        }
    }

    @Nonnull
    public List<Move> get() {
        return new ArrayList<>(MOVES);
    }

    @Nullable
    public Move get(String name) {
        name = name.toLowerCase();

        for (Move move : MOVES) {
            if (Objects.equals(move.getBaseMove().NAME.toLowerCase(), name)) {
                return move;
            }
        }

        return null;
    }

    @Nonnull
    public List<BaseMove> getValid() {
        return new ArrayList<>(VALID_MOVES);
    }

    public void resetValid() {
        VALID_MOVES.clear();
        for (Move move : MOVES) {
            VALID_MOVES.add(move.getBaseMove());
        }
    }

    public void setValid(@Nonnull BaseMove... moves) {
        VALID_MOVES.clear();
        VALID_MOVES.addAll(Arrays.asList(moves));
    }

    @Nonnull
    public Map<Move, Integer> getDisabled() {
        return new HashMap<>(DISABLED_MOVES);
    }

    public boolean hasDisabled() {
        return !DISABLED_MOVES.isEmpty();
    }

    private void disable(int duration, @Nonnull List<Move> moves) {
        for (Move move : moves) {
            DISABLED_MOVES.put(move, duration);
        }
    }

    public void disable(int duration, @Nonnull Move... moves) {
        List<Move> moveList = Arrays.asList(moves);
        disable(duration, moveList);
    }

    public void disable(int duration, @Nonnull Action... actions) {
        List<Move> moveList = new ArrayList<>();
        for (Action action : actions) {
            moveList.add(action.getMove());
        }
        disable(duration, moveList);
    }

    public boolean hasPP() {
        for (Move move : MOVES) {
            if (move.getPP() > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean hasAll(@Nonnull BaseMove... moves) {
        return MOVES.containsAll(Arrays.asList(moves));
    }

    public boolean hasOne(@Nonnull BaseMove... moves) {
        List<BaseMove> baseMoveList = Arrays.asList(moves);

        for (Move move : MOVES) {
            if (baseMoveList.contains(move.getBaseMove())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasOne(@Nonnull String... name) {
        BaseMove[] moves = Arrays.stream(name).map(BaseMove::getMove).toArray(BaseMove[]::new);
        return hasOne(moves);
    }

}
