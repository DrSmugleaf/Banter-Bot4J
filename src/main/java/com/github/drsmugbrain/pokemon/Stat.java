package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 08/10/2017.
 */
public class Stat implements IStat {

    @Nonnull
    private final IStat STAT;

    private final int IV;

    private final int EV;

    @Nonnull
    private Stage STAGE = Stage.ZERO;

    Stat(@Nonnull IStat stat, int iv, int ev) {
        STAT = stat;
        IV = iv;
        EV = ev;
    }

    @Nonnull
    public IStat getStat() {
        return STAT;
    }

    @Nonnull
    @Override
    public String getName() {
        return STAT.getName();
    }

    @Nonnull
    public String getAbbreviation() {
        return STAT.getAbbreviation();
    }

    public int getIV() {
        return IV;
    }

    public int getEV() {
        return EV;
    }

    public Stage getStage() {
        return STAGE;
    }

    protected void setStage(@Nonnull Stage stage) {
        STAGE = stage;
    }

}
