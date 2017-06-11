package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 10/06/2017.
 */
public class BaseMove {

    private static final Map<String, BaseMove> BASE_MOVES = new TreeMap<>();

    private final String NAME;
    private final Type TYPE;
    private final Category CATEGORY;
    private final int POWER;
    private final int ACCURACY;
    private final int PP;
    private final boolean IS_HIDDEN_POWER;

    protected BaseMove(@Nonnull String name, @Nonnull Type type, @Nonnull Category category, int power, int accuracy, int pp) {
        this.NAME = name;
        this.TYPE = type;
        this.CATEGORY = category;
        this.POWER = power;
        this.ACCURACY = accuracy;
        this.PP = pp;
        this.IS_HIDDEN_POWER = name.startsWith("Hidden Power ");
    }

    protected BaseMove(@Nonnull BaseMove basemove) {
        this(basemove.NAME, basemove.TYPE, basemove.CATEGORY, basemove.POWER, basemove.ACCURACY, basemove.PP);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Type getType() {
        return this.TYPE;
    }

    @Nonnull
    public Category getCategory() {
        return this.CATEGORY;
    }

    public int getPower() {
        return this.POWER;
    }

    public int getAccuracy() {
        return this.ACCURACY;
    }

    public int getPP() {
        return this.PP;
    }

    public boolean isHiddenPower() {
        return this.IS_HIDDEN_POWER;
    }

    public int getPriority() {
        return Priority.getPriority(this);
    }

    public void createBaseMove() {
        BaseMove.BASE_MOVES.put(this.NAME, this);
    }

    public static BaseMove getBaseMove(String name) {
        return BaseMove.BASE_MOVES.get(name);
    }

}
