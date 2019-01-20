package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.equipment.Hardpoints;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public class Hardpoint {

    private final Hardpoints TYPE;

    private final int SIZE;

    protected Hardpoint(@NotNull Hardpoints type, int size) {
        TYPE = type;
        SIZE = size;
    }

    @NotNull
    public Hardpoints getType() {
        return TYPE;
    }

    public int getSize() {
        return SIZE;
    }

}
