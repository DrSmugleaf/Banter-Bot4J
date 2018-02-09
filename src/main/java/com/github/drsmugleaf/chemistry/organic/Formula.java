package com.github.drsmugleaf.chemistry.organic;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 09/02/2018.
 */
public class Formula {

    public final String FORMULA_STRING;
    public final List<List<Elements>> FORMULA_LIST;

    protected Formula(@Nonnull FormulaBuilder builder) {
        FORMULA_STRING = builder.getFormulaString();
        FORMULA_LIST = builder.getFormulaList();
    }

}
