package com.github.drsmugleaf.chemistry.organic;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 09/02/2018.
 */
public class FormulaBuilder {

    private Elements[][] FORMULA_ARRAY;

    public FormulaBuilder(@Nonnull String string) {}

    public Formula build() {
        return new Formula(this);
    }

    public Elements[][] getFormulaArray() {
        return FORMULA_ARRAY.clone();
    }

    public String getFormulaString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < FORMULA_ARRAY.length; i++) {
            for (int j = 0; j < FORMULA_ARRAY[i].length; j++) {
                builder.append(FORMULA_ARRAY[i][j]);
            }
        }

        return builder.toString();
    }

}
