package com.github.drsmugleaf.chemistry.organic;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/02/2018.
 */
public class Formula {

    public final String FORMULA_STRING;
    public final Elements[][] FORMULA_ARRAY;

    protected Formula(@Nonnull FormulaBuilder builder) {
        FORMULA_STRING = builder.getFormulaString();
        FORMULA_ARRAY = builder.getFormulaArray();
    }

}
