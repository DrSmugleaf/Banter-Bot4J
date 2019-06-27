package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Action;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Created by DrSmugleaf on 19/10/2017.
 */
public class Moveset {

    private final List<Move> MOVES;
    private final List<BaseMove> VALID_MOVES = new ArrayList<>();
    private final Map<BaseMove, Integer> DISABLED_MOVES = new HashMap<>();

    public Moveset(List<Move> moves) {
        MOVES = new ArrayList<>(moves);
        for (Move move : MOVES) {
            VALID_MOVES.add(move.BASE_MOVE);
        }
    }

    @Contract(" -> new")
    public List<Move> get() {
        return new ArrayList<>(MOVES);
    }

    @Nullable
    public Move get(String name) {
        name = name.toLowerCase();

        for (Move move : MOVES) {
            if (Objects.equals(move.BASE_MOVE.NAME.toLowerCase(), name)) {
                return move;
            }
        }

        return null;
    }

    @Contract(" -> new")
    public List<BaseMove> getValid() {
        return new ArrayList<>(VALID_MOVES);
    }

    public void resetValid() {
        VALID_MOVES.clear();
        for (Move move : MOVES) {
            VALID_MOVES.add(move.BASE_MOVE);
        }
    }

    public void setValid(BaseMove... moves) {
        VALID_MOVES.clear();
        VALID_MOVES.addAll(Arrays.asList(moves));
    }

    @Contract(" -> new")
    public Map<BaseMove, Integer> getDisabled() {
        return new HashMap<>(DISABLED_MOVES);
    }

    public boolean hasDisabled() {
        return !DISABLED_MOVES.isEmpty();
    }

    public void disable(int duration, List<BaseMove> moves) {
        for (BaseMove move : moves) {
            DISABLED_MOVES.put(move, duration);
        }
    }

    public void disable(int duration, BaseMove... moves) {
        List<BaseMove> moveList = Arrays.asList(moves);
        disable(duration, moveList);
    }

    public void disable(int duration, Action... actions) {
        List<BaseMove> moveList = new ArrayList<>();
        for (Action action : actions) {
            moveList.add(action.BASE_MOVE);
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

    public boolean hasAll(BaseMove... moves) {
        return MOVES.containsAll(Arrays.asList(moves));
    }

    public boolean hasOne(BaseMove... moves) {
        List<BaseMove> baseMoveList = Arrays.asList(moves);

        for (Move move : MOVES) {
            if (baseMoveList.contains(move.BASE_MOVE)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasOne(String... name) {
        BaseMove[] moves = Arrays.stream(name).map(BaseMove::getMove).toArray(BaseMove[]::new);
        return hasOne(moves);
    }

}
