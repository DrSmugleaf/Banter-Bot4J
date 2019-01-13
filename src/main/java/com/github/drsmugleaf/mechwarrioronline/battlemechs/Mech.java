package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public class Mech extends BattleMech {

    @NotNull
    private static final DecimalFormat FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.US);

    static {
        DecimalFormatSymbols symbols = FORMATTER.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        FORMATTER.setDecimalFormatSymbols(symbols);
    }

    @NotNull
    private final Loadout LOADOUT;

    protected Mech(@NotNull BattleMech mech) {
        super(mech);
        LOADOUT = new Loadout(getSections());
    }

    @NotNull
    public Loadout getLoadout() {
        return LOADOUT;
    }

    @NotNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("**")
                .append(getModel().getName())
                .append(" (")
                .append(getWeight())
                .append(")**: ")
                .append(getName())
                .append(" (")
                .append(getMinimumEngine())
                .append("-")
                .append(getMaximumEngine())
                .append(")");

        if (getCostMC() != null || getCostCBills() != null) {
            builder.append(", Cost: ");
        }

        if (getCostMC() != null) {
            builder
                    .append(FORMATTER.format(getCostMC()))
                    .append(" MC");

            if (getCostCBills() != null) {
                builder.append(", ");
            }
        }

        if (getCostCBills() != null) {
            builder
                    .append(FORMATTER.format(getCostCBills()))
                    .append(" CBills");
        }

        return builder.toString();
    }

}
