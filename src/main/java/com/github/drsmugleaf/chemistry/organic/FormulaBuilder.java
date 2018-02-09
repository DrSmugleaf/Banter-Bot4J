package com.github.drsmugleaf.chemistry.organic;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 09/02/2018.
 */
public class FormulaBuilder {

    private List<List<Elements>> FORMULA = new ArrayList<>();

    public FormulaBuilder() {}

    @Nonnull
    public Formula build() {
        return new Formula(this);
    }

    @Nonnull
    public List<List<Elements>> getFormulaList() {
        List<List<Elements>> list = new ArrayList<>(FORMULA.size());

        for (int i = 0; i < FORMULA.size(); i++) {
            List<Elements> line = FORMULA.get(i);
            list.set(i, new ArrayList<>(line.size()));

            for (int j = 0; j < line.size(); j++) {
                list.get(i).set(j, line.get(j));
            }
        }

        return list;
    }

    @Nonnull
    public String getFormulaString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < FORMULA.size(); i++) {
            for (int j = 0; j < FORMULA.get(i).size(); j++) {
                builder.append(FORMULA.get(i).get(j));
            }
        }

        return builder.toString();
    }

}
