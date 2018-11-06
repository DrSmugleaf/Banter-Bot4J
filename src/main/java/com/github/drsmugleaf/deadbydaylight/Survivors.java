package com.github.drsmugleaf.deadbydaylight;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Survivors {

    ACE_VISCONTI("Ace Visconti"),
    ADAM_FRANCIS("Adam Francis"),
    BILL_OVERBECK("Bill Overbeck"),
    CLAUDETTE_MOREL("Claudette Morel"),
    DAVID_KING("David King"),
    DAVID_TAPP("David Tapp"),
    DWIGHT_FAIRFIELD("Dwight Fairfield"),
    FENG_MIN("Feng Min"),
    JAKE_PARK("Jake Park"),
    KATE_DENSON("Kate Denson"),
    LAURIE_STRODE("Laurie Strode"),
    MEG_THOMAS("Meg Thomas"),
    NEA_KARLSSON("Nea Karlsson"),
    QUENTIN_SMITH("Quentin Smith");

    @Nonnull
    public final String NAME;

    Survivors(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
