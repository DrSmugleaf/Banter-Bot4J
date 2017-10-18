package com.github.drsmugbrain.pokemon.moves;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 19/10/2017.
 */
public class Moves {

    @Nonnull
    private final List<Move> MOVES;

    @Nonnull
    private final List<BaseMove> VALID_MOVES = new ArrayList<>();

    public Moves(@Nonnull List<Move> moves) {
        MOVES = new ArrayList<>(moves);
    }

    @Nonnull
    public List<Move> get() {
        return new ArrayList<>(MOVES);
    }

    @Nullable
    public Move get(String name) {
        name = name.toLowerCase();

        for (Move move : MOVES) {
            if (Objects.equals(move.getBaseMove().NAME, name)) {
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
