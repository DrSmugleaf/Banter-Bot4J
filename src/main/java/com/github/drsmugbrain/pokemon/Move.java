package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Move implements Comparable<Move> {

    private static final Set<Move> BASE_MOVES = new TreeSet<>();

    private final String NAME;

    private final Type TYPE;

    private final Category CATEGORY;
    private final int POWER;
    private final int ACCURACY;
    private final int PP;

    public Move(@Nonnull String name, @Nonnull Type type, @Nonnull Category category, int power, int accuracy, int pp) {
        this.NAME = name;

        this.TYPE = type;

        this.CATEGORY = category;
        this.POWER = power;
        this.ACCURACY = accuracy;
        this.PP = pp;
    }

    @Override
    public int compareTo(@Nonnull Move move) {
        return this.NAME.compareTo(move.NAME);
    }

    void createBaseMove() {
        Move.BASE_MOVES.add(this);
    }

    @Nonnull
    public static Set<Move> getBaseMoves() {
        return Move.BASE_MOVES;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

}
